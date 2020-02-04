package de.sanit4u.test.check.service;

import java.util.List;

import de.sanit4u.test.check.model.CurrencyExchangeRate;

public interface ForexDataService {

	void checkNUpdateForexServiceForLatestBTC2USDRate();

	List<CurrencyExchangeRate> getHistoricalDataForBTC2USD(String startDate, String endDate);
}
