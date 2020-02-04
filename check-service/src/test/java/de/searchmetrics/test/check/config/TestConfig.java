package de.searchmetrics.test.check.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import de.searchmetrics.test.check.service.ExchangeRateWriteService;
import de.searchmetrics.test.check.service.ForexDataService;

@Profile("test")
@Configuration
@PropertySource("test.properties")
public class TestConfig {

	@Bean
	@Primary
	public ForexDataService forexService() {
		return Mockito.mock(ForexDataService.class);
	}

	@Bean
	@Primary
	public ExchangeRateWriteService currencyService() {
		return Mockito.mock(ExchangeRateWriteService.class);
	}
}
