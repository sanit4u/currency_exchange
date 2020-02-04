package de.sanit4u.test.check.repo;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.sanit4u.test.check.model.Currency;
import de.sanit4u.test.check.model.CurrencyExchangeRate;

@Repository
public interface ExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, String> {

	boolean existsByTimestampAndFromCurrencyAndToCurrency(LocalDateTime timestamp, Currency fromCurrency,
			Currency toCurrency);

}
