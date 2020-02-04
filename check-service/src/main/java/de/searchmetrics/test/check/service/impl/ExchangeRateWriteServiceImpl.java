package de.searchmetrics.test.check.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.searchmetrics.test.check.model.CurrencyExchangeRate;
import de.searchmetrics.test.check.repo.ExchangeRateRepository;
import de.searchmetrics.test.check.service.ExchangeRateWriteService;

@Service
public class ExchangeRateWriteServiceImpl implements ExchangeRateWriteService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private ExchangeRateRepository exchangeRateRepository;

	@Autowired
	public ExchangeRateWriteServiceImpl(ExchangeRateRepository exchangeRateRepository) {

		this.exchangeRateRepository = exchangeRateRepository;

	}

	@Transactional
	@Override
	public boolean insertRate(CurrencyExchangeRate currencyRate) {
		log.info(" inserting currency rate ");
		CurrencyExchangeRate savedEntity = exchangeRateRepository.save(currencyRate);

		return savedEntity != null;
	}

	@Transactional
	@Override
	public boolean insertRateBulk(List<CurrencyExchangeRate> currencyRateList) {

		log.info(" inserting bulk currency rate ");
		List<CurrencyExchangeRate> requiredRecords2Insert = checkForMissingRecords(currencyRateList);

		if(requiredRecords2Insert.isEmpty()) {
			return true;
		}
		
		List<CurrencyExchangeRate> saveAll = exchangeRateRepository.saveAll(requiredRecords2Insert);

		return !saveAll.isEmpty();
	}

	private List<CurrencyExchangeRate> checkForMissingRecords(List<CurrencyExchangeRate> currencyRateList) {
		List<CurrencyExchangeRate> filteredHistoricalList = new ArrayList<CurrencyExchangeRate>();

		for (CurrencyExchangeRate currencyExchangeRate : currencyRateList) {

			if (!this.isRecordExists(currencyExchangeRate)) {
				filteredHistoricalList.add(currencyExchangeRate);
			}
		}
		return filteredHistoricalList;
	}

	public boolean isRecordExists(CurrencyExchangeRate record) {

		log.info(String.format("Checking for record having currency %s_%s ", record.getFromCurrency(),
				record.getToCurrency()));
		return exchangeRateRepository.existsByTimestampAndFromCurrencyAndToCurrency(record.getTimestamp(),
				record.getFromCurrency(), record.getToCurrency());

	}

}
