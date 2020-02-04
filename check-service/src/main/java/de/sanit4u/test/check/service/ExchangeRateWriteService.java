package de.sanit4u.test.check.service;

import java.util.List;

import de.sanit4u.test.check.model.CurrencyExchangeRate;

public interface ExchangeRateWriteService {

	boolean insertRate(CurrencyExchangeRate currencyRate);

	boolean insertRateBulk(List<CurrencyExchangeRate> currencyRateList);
}
