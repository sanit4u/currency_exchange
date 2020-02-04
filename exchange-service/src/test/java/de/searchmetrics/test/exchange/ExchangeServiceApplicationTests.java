package de.searchmetrics.test.exchange;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.searchmetrics.test.exchange.client.CheckClient;
import de.searchmetrics.test.exchange.model.CurrencyExchangeRate;
import de.searchmetrics.test.exchange.model.RestResponse;
import de.searchmetrics.test.exchange.service.ExchangeRateReadService;

@RunWith(SpringRunner.class)
@SpringBootTest
class ExchangeServiceApplicationTests {

	@Mock
	public CheckClient mockCheckClient;

	@Mock
	public ExchangeRateReadService mockReadService;

	@Test
	public void testCheckClient() {
		String startDate = "2019-10-20";
		String endDate = "2019-10-22";

		CurrencyExchangeRate c1 = new CurrencyExchangeRate();
		c1.setRate(new BigDecimal("9827"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime fromDateTime = LocalDate.parse(startDate, formatter).atTime(LocalTime.MAX);
		c1.setTimestamp(fromDateTime);

		CurrencyExchangeRate c2 = new CurrencyExchangeRate();
		c2.setRate(new BigDecimal("9825"));
		LocalDateTime toDateTime = LocalDate.parse(endDate, formatter).atTime(LocalTime.MAX);
		c2.setTimestamp(toDateTime);

		List<CurrencyExchangeRate> list = Arrays.asList(c1, c2);
		RestResponse<List<CurrencyExchangeRate>> expectedResponse = RestResponse.create(200, "successfully retrieved.",
				list);

		when(mockCheckClient.getHistoricalData(startDate, endDate)).thenReturn(expectedResponse);

		RestResponse<List<CurrencyExchangeRate>> actualResponse = mockCheckClient.getHistoricalData(startDate, endDate);
		assertNotNull(actualResponse);

	}

	@Test
	public void testGetLatest() {
		CurrencyExchangeRate c1 = new CurrencyExchangeRate();
		c1.setRate(new BigDecimal("9827"));
		c1.setTimestamp(LocalDateTime.now());

		when(mockReadService.getLatestRate()).thenReturn(c1);

		CurrencyExchangeRate actualResponse = mockReadService.getLatestRate();
		assertNotNull(actualResponse);

	}

	@Test
	public void testGetHisotryData() {
		String startDate = "2019-10-20";
		String endDate = "2019-10-22";

		CurrencyExchangeRate c1 = new CurrencyExchangeRate();
		c1.setRate(new BigDecimal("9827"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime fromDateTime = LocalDate.parse(startDate, formatter).atTime(LocalTime.MAX);
		c1.setTimestamp(fromDateTime);

		CurrencyExchangeRate c2 = new CurrencyExchangeRate();
		c2.setRate(new BigDecimal("9825"));
		LocalDateTime toDateTime = LocalDate.parse(endDate, formatter).atTime(LocalTime.MAX);
		c2.setTimestamp(toDateTime);

		List<CurrencyExchangeRate> list = Arrays.asList(c1, c2);

		when(mockReadService.getHistoricalRate(startDate, endDate)).thenReturn(list);

		List<CurrencyExchangeRate> actualResponse = mockReadService.getHistoricalRate(startDate, endDate);
		assertNotNull(actualResponse);

	}
}
