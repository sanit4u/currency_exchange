package de.searchmetrics.test.exchange;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.searchmetrics.test.exchange.exception.ExchangeServiceException;
import de.searchmetrics.test.exchange.exception.InvalidInputException;
import de.searchmetrics.test.exchange.model.RestResponse;

/**
 * Global Exception Handler for exchange service
 *
 */
@ControllerAdvice
public class ExchangeExceptionHandler {

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<RestResponse<String>> invalidInput(InvalidInputException ex) {
		RestResponse<String> response = RestResponse.create(400, ex.getMessage());

		return new ResponseEntity<RestResponse<String>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExchangeServiceException.class)
	public ResponseEntity<RestResponse<String>> resourceNotFound(ExchangeServiceException ex) {
		RestResponse<String> response = RestResponse.create(500, ex.getMessage() + ex.getCause().getMessage());

		return new ResponseEntity<RestResponse<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
