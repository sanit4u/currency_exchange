package de.searchmetrics.test.exchange.service;

import java.util.List;

import de.searchmetrics.test.exchange.model.CurrencyExchangeRate;

public interface ExchangeRateReadService {

	CurrencyExchangeRate getLatestRate();

	List<CurrencyExchangeRate> getHistoricalRate(String StartDate, String endDate);
}
