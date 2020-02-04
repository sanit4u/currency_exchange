package de.searchmetrics.test.check.service;

import java.util.List;

import de.searchmetrics.test.check.model.CurrencyExchangeRate;

public interface ForexDataService {

	void checkNUpdateForexServiceForLatestBTC2USDRate();

	List<CurrencyExchangeRate> getHistoricalDataForBTC2USD(String startDate, String endDate);
}
