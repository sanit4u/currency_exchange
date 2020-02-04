package de.searchmetrics.test.check.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.searchmetrics.test.check.util.Constants;

/**
 * Forex client to retrieve the latest rate and historical data
 *
 */
@FeignClient(name = "free.currencyconverterapi.com", url = "${check.forex.uri}")
public interface ForexClient {

	@RequestMapping(method = RequestMethod.GET)
	String getLatestRate(@RequestParam(name = "q") String currencyFromTo,
			@RequestParam(name = "compact", defaultValue = Constants.FOREX_QUERY_COMPACT_DEFAULT_VALUE) String compact,
			@RequestParam(name = "apiKey") String apiKey);

	@RequestMapping(method = RequestMethod.GET)
	String getHistoricalData(@RequestParam(name = "q") String currencyFromTo,
			@RequestParam(name = "compact", defaultValue = Constants.FOREX_QUERY_COMPACT_DEFAULT_VALUE) String compact,
			@RequestParam(name = "apiKey") String apiKey, @RequestParam(name = "date") String startDate,
			@RequestParam(name = "endDate") String endDate);
}
