package de.sanit4u.test.exchange.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.sanit4u.test.exchange.model.CurrencyExchangeRate;
import de.sanit4u.test.exchange.model.RestResponse;

/**
 * Check client to retrieve the historical data.
 *
 */
@FeignClient(name = "check-service", url = "${exchange.check.uri}")
public interface CheckClient {

	@RequestMapping(method = RequestMethod.GET, value = "/history")
	RestResponse<List<CurrencyExchangeRate>> getHistoricalData(@RequestParam(name = "start") String startDate,
			@RequestParam(name = "end") String endDate);
}
