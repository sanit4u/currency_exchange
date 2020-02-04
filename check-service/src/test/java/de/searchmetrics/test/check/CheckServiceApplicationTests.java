package de.searchmetrics.test.check;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import de.searchmetrics.test.check.config.SchedulerConfig;
import de.searchmetrics.test.check.config.TestConfig;
import de.searchmetrics.test.check.model.CurrencyExchangeRate;
import de.searchmetrics.test.check.scheduler.CheckServiceScheduler;
import de.searchmetrics.test.check.service.ExchangeRateWriteService;
import de.searchmetrics.test.check.service.ForexDataService;

@ActiveProfiles("test")
@SpringJUnitConfig({ SchedulerConfig.class, TestConfig.class })
class CheckServiceApplicationTests {

	@SpyBean
	CheckServiceScheduler scheduler;

	@Autowired
	private ForexDataService forexService;

	@Autowired
	private ExchangeRateWriteService currencyService;

	@Test
	public void testSchedulerJob() {

		Mockito.when(currencyService.insertRate(Mockito.any())).thenReturn(Boolean.TRUE);

		// @formatter:off
		await()
		.atMost(Duration.TEN_SECONDS)
		.untilAsserted(() -> verify(scheduler, times(2))
				.checkNUpdateRates());
		// @formatter:on
	}

	@Test
	public void testForexDataService() {
		ForexDataService mock = spy(forexService);
		Mockito.when(currencyService.insertRateBulk(Mockito.anyList())).thenReturn(Boolean.TRUE);

		List<CurrencyExchangeRate> historicalDataForBTC2USD = mock.getHistoricalDataForBTC2USD("2019-10-20",
				"2019-10-22");

		assertTrue(historicalDataForBTC2USD.isEmpty());

	}

}
