package de.sanit4u.test.exchange.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import de.sanit4u.test.exchange.client.CheckClient;
import de.sanit4u.test.exchange.exception.ExchangeServiceException;
import de.sanit4u.test.exchange.model.CurrencyExchangeRate;
import de.sanit4u.test.exchange.model.RestResponse;
import de.sanit4u.test.exchange.repository.ExchangeRateRepository;
import de.sanit4u.test.exchange.service.ExchangeRateReadService;

/**
 * Exchange rate read service reads the db for latest currency rate and
 * historical data.
 *
 */
@Service
public class ExchangeRateReadServiceImpl implements ExchangeRateReadService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	private CheckClient checkClient;

	@Autowired
	public ExchangeRateReadServiceImpl(CheckClient checkClient, ExchangeRateRepository exchangeRateRepository) {
		this.exchangeRateRepository = exchangeRateRepository;
		this.checkClient = checkClient;
	}

	/**
	 * Retrieves the latest currency rate of BTC 2 USD.
	 */
	@Transactional
	@Override
	public CurrencyExchangeRate getLatestRate() {
		log.info("retrieving latest exchange rate.");
		CurrencyExchangeRate latestRate = null;

		try {

			latestRate = exchangeRateRepository.findLatestRecord();

		} catch (Exception e) {
			log.error("Error while fetching latest exchange rate :", e);
			throw new ExchangeServiceException("Error while fetching latest exchange rate :", e);
		}

		return latestRate;
	}

	/**
	 * Retrieves the historical rate in the range of start date and end date First,
	 * it checks in the db, if it gets all the data per date, then it returns the
	 * result. If, it does not get all the data, then it calls the
	 * {@code CheckClient#getHistoricalData(String, String)} to retrieve the
	 * historical data.
	 */
	@Transactional
	@Override
	public List<CurrencyExchangeRate> getHistoricalRate(String StartDate, String endDate) {

		log.info(String.format("retrieving historical exchange rate from date %s to date %s", StartDate, endDate));

		List<CurrencyExchangeRate> historicalDataInRange = null;
		try {

			LocalDateTime start = getLocalDateTime(StartDate, true);
			LocalDateTime end = getLocalDateTime(endDate, false);

			historicalDataInRange = exchangeRateRepository.findAllByTimestampBetween(start, end);

			// @formatter:off
			Map<LocalDate, CurrencyExchangeRate> historicalRecord = historicalDataInRange.stream()
				.collect(
						Collectors.toMap(currencyExcahange-> currencyExcahange.getTimestamp().toLocalDate(), 
								Function.identity(),
								BinaryOperator.maxBy(
										Comparator.comparing(CurrencyExchangeRate::getTimestamp)
										)
								)
						);
			// @formatter:on

			LocalDateTime tempDateTime = LocalDateTime.from(start);
			long days = tempDateTime.until(end, ChronoUnit.DAYS) + 1;

			if ((historicalRecord.isEmpty() || historicalRecord.size() < days)) {

				RestResponse<List<CurrencyExchangeRate>> historicalDataResponse = checkClient
						.getHistoricalData(StartDate, endDate);

				if (historicalDataResponse.getCode() != HttpStatus.OK.value()) {
					throw new ExchangeServiceException("Feign client is not working.");
				}

				return historicalDataResponse.getResults();

			}

			historicalDataInRange = historicalRecord.values().stream().collect(Collectors.toList());

		} catch (Exception e) {
			log.error("Error while fetching historical exchange rate :", e);
			throw new ExchangeServiceException("Error while fetching historical exchange rate :", e);

		}

		return historicalDataInRange;
	}

	private LocalDateTime getLocalDateTime(String dateString, boolean isStart) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDateTime time = isStart ? LocalDate.parse(dateString, formatter).atStartOfDay()
				: LocalDate.parse(dateString, formatter).atStartOfDay().plusDays(1).minusSeconds(1);
		return time;
	}

}
