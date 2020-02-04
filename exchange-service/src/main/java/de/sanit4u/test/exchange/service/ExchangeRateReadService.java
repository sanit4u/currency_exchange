package de.sanit4u.test.exchange.service;

import java.util.List;

import de.sanit4u.test.exchange.model.CurrencyExchangeRate;

public interface ExchangeRateReadService {

	CurrencyExchangeRate getLatestRate();

	List<CurrencyExchangeRate> getHistoricalRate(String StartDate, String endDate);
}
