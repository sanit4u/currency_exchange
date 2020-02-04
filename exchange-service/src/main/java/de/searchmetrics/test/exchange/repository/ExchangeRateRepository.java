package de.searchmetrics.test.exchange.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.searchmetrics.test.exchange.model.CurrencyExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, String> {

	@Query(nativeQuery = true, value = "SELECT * FROM currency_exchange_rate r ORDER BY r.time_stamp DESC LIMIT 1")
	CurrencyExchangeRate findLatestRecord();

	@Query(nativeQuery = true, value = "SELECT r.time_stamp FROM currency_exchange_rate r ORDER BY r.time_stamp ASC LIMIT 1")
	LocalDateTime findOldestDateTime();

	@Query("SELECT r FROM CurrencyExchangeRate r where r.timestamp between ?1 and ?2")
	List<CurrencyExchangeRate> findAllByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

}
