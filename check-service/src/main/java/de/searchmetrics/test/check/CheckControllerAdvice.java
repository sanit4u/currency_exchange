package de.searchmetrics.test.check;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.searchmetrics.test.check.exception.CheckServiceException;
import de.searchmetrics.test.check.exception.SchedulerException;
import de.searchmetrics.test.check.exception.BadRequestCheckException;
import de.searchmetrics.test.check.model.RestResponse;

@ControllerAdvice
public class CheckControllerAdvice {

	@ExceptionHandler({ CheckServiceException.class, SchedulerException.class })
	public ResponseEntity<RestResponse<String>> internalServerException(RuntimeException ex) {
		RestResponse<String> response = RestResponse.create(500, ex.getMessage() + " : " + ex.getCause().getMessage());

		return new ResponseEntity<RestResponse<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BadRequestCheckException.class)
	public ResponseEntity<RestResponse<String>> badRequestFound(BadRequestCheckException ex) {
		RestResponse<String> response = RestResponse.create(400, ex.getMessage());

		return new ResponseEntity<RestResponse<String>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RestResponse<String>> general(Exception ex) {
		RestResponse<String> response = RestResponse.create(500, ex.getMessage());

		return new ResponseEntity<RestResponse<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
