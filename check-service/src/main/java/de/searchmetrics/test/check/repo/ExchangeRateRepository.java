package de.searchmetrics.test.check.repo;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.searchmetrics.test.check.model.Currency;
import de.searchmetrics.test.check.model.CurrencyExchangeRate;

@Repository
public interface ExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, String> {

	boolean existsByTimestampAndFromCurrencyAndToCurrency(LocalDateTime timestamp, Currency fromCurrency,
			Currency toCurrency);

}
