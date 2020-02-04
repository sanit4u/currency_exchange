package de.searchmetrics.test.exchange.web;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.searchmetrics.test.exchange.aspect.CustomDateParams;
import de.searchmetrics.test.exchange.model.CurrencyExchangeRate;
import de.searchmetrics.test.exchange.model.RestResponse;
import de.searchmetrics.test.exchange.service.ExchangeRateReadService;
import de.searchmetrics.test.exchange.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Exchange Service Rest API end point
 *
 */
@RestController
@RequestMapping(Constants.CONST_REST_CONTROLLER_PATH_EXCHANGE)
@Api(value = "Exchange", description = "Exchange Service Rest API end point", tags = { "Exchange" })
public class ExchangeController {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeRateReadService exchangeRateReadService;

	/**
	 * This API retrieves the latest exchange rate for BTC to USD.
	 * 
	 * 
	 */
	@ApiOperation(value = "Retrieves Latest exchange rate")
	@GetMapping(path = "/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getLatestRate() {

		CurrencyExchangeRate latestRate = exchangeRateReadService.getLatestRate();
		RestResponse<CurrencyExchangeRate> response = RestResponse.create(HttpStatus.OK.value(),
				"Latest rate retrieved successfully.", latestRate);

		return ResponseEntity.ok().body(response);
	}

	/**
	 * This API retrieves the historical exchange rate for BTC to USD.
	 * 
	 * 
	 */
	@CustomDateParams
	@ApiOperation(value = "Retrieves history exchange rate from start date to end date ; date format is yyyy-mm-dd")
	@GetMapping(path = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHistoricalData(@RequestParam("start") @NotNull String startDate,
			@RequestParam("end") @NotNull String endDate) {

		List<CurrencyExchangeRate> historicalData = exchangeRateReadService.getHistoricalRate(startDate, endDate);
		RestResponse<List<CurrencyExchangeRate>> response = RestResponse.create(HttpStatus.OK.value(),
				"All Historical Data is Retrieved Successfully.", historicalData);

		return ResponseEntity.ok().body(response);
	}

}
