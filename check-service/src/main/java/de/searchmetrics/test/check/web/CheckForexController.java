package de.searchmetrics.test.check.web;

import java.util.List;

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

import de.searchmetrics.test.check.model.CurrencyExchangeRate;
import de.searchmetrics.test.check.model.RestResponse;
import de.searchmetrics.test.check.service.ForexDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/check")
@Api(value = "Check", description = "Check Service Rest API end point", tags = { "Check" })
public class CheckForexController {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ForexDataService forexDataService;

	/**
	 * This API checks the forex exchange for historical data.
	 * 
	 * 
	 */
	@ApiOperation(value = "Retrieves Historical Data; date format is yyyy-mm-dd")
	@GetMapping(path = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHistoricalData(@RequestParam("start") String startDate,
			@RequestParam("end") String endDate) {

		List<CurrencyExchangeRate> historicalData = forexDataService.getHistoricalDataForBTC2USD(startDate, endDate);
		RestResponse<List<CurrencyExchangeRate>> response = RestResponse.create(HttpStatus.OK.value(),
				"All Data Retrieved", historicalData);

		return ResponseEntity.ok().body(response);
	}

}
