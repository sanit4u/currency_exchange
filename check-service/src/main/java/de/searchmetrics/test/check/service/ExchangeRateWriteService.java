package de.searchmetrics.test.check.service;

import java.util.List;

import de.searchmetrics.test.check.model.CurrencyExchangeRate;

public interface ExchangeRateWriteService {

	boolean insertRate(CurrencyExchangeRate currencyRate);

	boolean insertRateBulk(List<CurrencyExchangeRate> currencyRateList);
}
