package de.sanit4u.test.check.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "CurrencyExchangeRate")
@Table(name = "currency_exchange_rate")
public class CurrencyExchangeRate {

	@Basic(optional = false)
	@Column(name = "rate_id", nullable = false, unique = true, updatable = false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Id()
	private String rateId;

	@Basic(optional = false)
	@Column(name = "from_currency")
	@Enumerated(EnumType.STRING)
	private Currency fromCurrency;

	@Basic(optional = false)
	@Column(name = "to_currency")
	@Enumerated(EnumType.STRING)
	private Currency toCurrency;

	@Basic(optional = false)
	@Column(name = "rate")
	private BigDecimal rate;

	@Basic(optional = false)
	@Column(name = "time_stamp", nullable = false, updatable = false, columnDefinition = "timestamp without time zone DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime timestamp;

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public Currency getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(Currency fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Currency getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(Currency toCurrency) {
		this.toCurrency = toCurrency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}
