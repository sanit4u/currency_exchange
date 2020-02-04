package de.searchmetrics.test.check.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.searchmetrics.test.check.client.ForexClient;
import de.searchmetrics.test.check.exception.BadRequestCheckException;
import de.searchmetrics.test.check.exception.CheckServiceException;
import de.searchmetrics.test.check.model.Currency;
import de.searchmetrics.test.check.model.CurrencyExchangeRate;
import de.searchmetrics.test.check.service.ExchangeRateWriteService;
import de.searchmetrics.test.check.service.ForexDataService;
import de.searchmetrics.test.check.util.Constants;
import feign.FeignException;

/**
 * Forex service uses {@code ForexClient} for retrieves the latest BTC 2 USD
 * conversion rate as well as historical data.
 * 
 *
 */
@Service
public class ForexDataServiceImpl implements ForexDataService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${check.forex.uri:https://free.currconv.com/api/v7/convert}")
	private String API;

	private ForexClient forexClient;

	private ExchangeRateWriteService currencyService;

	@Value("${check.api.key:3f0617a5a31c375c46a5}")
	private String apiKey;

	@Autowired
	public ForexDataServiceImpl(ForexClient forexClient, ExchangeRateWriteService exchangeRateWriteService) {
		this.forexClient = forexClient;
		this.currencyService = exchangeRateWriteService;
	}

	/**
	 * returns historical data for BTC 2 USD in a given date range. It uses
	 * {@code ForexClient} to retrieve the data.
	 */
	@Override
	public List<CurrencyExchangeRate> getHistoricalDataForBTC2USD(String start, String end) {
		List<CurrencyExchangeRate> historicalData = new ArrayList<CurrencyExchangeRate>();
		try {
			final String currencyFromTo = Currency.BTC.name() + "_" + Currency.USD.name();

			final String historicalDataResponse = forexClient.getHistoricalData(currencyFromTo,
					Constants.FOREX_QUERY_COMPACT_DEFAULT_VALUE, apiKey, start, end);

			this.deserialize(historicalDataResponse, currencyFromTo, Currency.BTC, Currency.USD, historicalData);

			if (!currencyService.insertRateBulk(historicalData)) {
				throw new CheckServiceException("Exception while saving bulk historical data.");
			}

		} catch (FeignException.BadRequest e) {

			log.error("Bad Request exception  : ", e);
			throw new BadRequestCheckException("Bad Request exception ", e);
		} catch (Exception e) {

			log.error("Error while fetching the historical forex response  : ", e);
			throw new CheckServiceException("Error while fetching the historical forex response  : ", e);
		}

		return historicalData;
	}

	/**
	 * returns the latest exchange rate for BTC 2 USD
	 */
	@Override
	public void checkNUpdateForexServiceForLatestBTC2USDRate() {
		final String currencyFromTo = Currency.BTC.name() + "_" + Currency.USD.name();
		log.info(String.format("checking the forex service %s for currency exchange rate of %s", API, currencyFromTo));
		try {

			final String forexResponse = forexClient.getLatestRate(currencyFromTo,
					Constants.FOREX_QUERY_COMPACT_DEFAULT_VALUE, apiKey);

			final CurrencyExchangeRate currencyRate = new CurrencyExchangeRate();
			deserialize(forexResponse, currencyFromTo, Currency.BTC, Currency.USD, currencyRate);

			currencyService.insertRate(currencyRate);

			log.debug(String.format("saved the currency rate for query %s", currencyFromTo));

		} catch (FeignException e) {
			log.error("Error accessing the feign client : ", e);
			throw new CheckServiceException("Error accessing the feign client : ", e);
		} catch (JsonProcessingException e) {
			log.error("Error while parsing the forex response : ", e);
			throw new CheckServiceException("Error while parsing the forex response : ", e);
		} catch (Exception e) {
			log.error("Error while while calling forex client : ", e);
			throw new CheckServiceException("Error while while calling forex client : ", e);
		}

		log.info(String.format("Check forex service complete for %s for query %s.", API, currencyFromTo));
	}

	// helper methods
	/**
	 * 
	 * 
	 * 
	 * @param <T>
	 * @param forexResponse
	 * @param key
	 * @param from
	 * @param to
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("unchecked")
	private <T> void deserialize(String forexResponse, String key, Currency from, Currency to, T t)
			throws JsonMappingException, JsonProcessingException {

		JsonNode jsonNode = new ObjectMapper().readTree(forexResponse);
		JsonNode value = jsonNode.get(key);
		if (value.isObject()) {

			List<CurrencyExchangeRate> list = (List<CurrencyExchangeRate>) t;
			Iterator<String> dates = value.fieldNames();
			while (dates.hasNext()) {
				String date = dates.next();

				JsonNode valueNode = value.get(date);
				String rate = valueNode.asText();

				CurrencyExchangeRate exchangeRate = new CurrencyExchangeRate();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime dateTime = LocalDate.parse(date, formatter).atStartOfDay().plusDays(1).minusSeconds(1);
				exchangeRate.setTimestamp(dateTime);

				deserialize(from, to, exchangeRate, rate, dateTime);

				list.add(exchangeRate);
			}

		} else {
			deserialize(from, to, t, value.asText(), LocalDateTime.now());
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @param t
	 * @param rateString
	 */
	private <T> void deserialize(Currency from, Currency to, T t, String rateString, LocalDateTime dateTime) {
		CurrencyExchangeRate exchangeRate = (CurrencyExchangeRate) t;
		exchangeRate.setTimestamp(dateTime);
		exchangeRate.setFromCurrency(from);
		exchangeRate.setToCurrency(to);
		exchangeRate.setRate(new BigDecimal(rateString));
	}
}
