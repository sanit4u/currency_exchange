package de.searchmetrics.test.exchange.aspect;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.searchmetrics.test.exchange.exception.InvalidInputException;

@Aspect
@Component
public class CustomDateParamValidator {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Around("@annotation(de.searchmetrics.test.exchange.aspect.CustomDateParams)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		log.debug("validating the start date and end date");
		try {

			Object[] value = joinPoint.getArgs();
			if (value[0] == null || value[1] == null) {
				throw new InvalidInputException("Invalid request parameters, Please provide dates.");
			}

			if (String.valueOf(value[0]).isEmpty()) {
				throw new InvalidInputException("Invalid request parameters, Please provide dates.");
			}
			if (String.valueOf(value[1]).isEmpty()) {
				throw new InvalidInputException("Invalid request parameters, Please provide dates.");
			}

			LocalDateTime start = this.getLocalDateTime(String.valueOf(value[0]));
			LocalDateTime end = this.getLocalDateTime(String.valueOf(value[1]));

			if (start.compareTo(end) > 0) {
				throw new InvalidInputException("Invalid request parameters, Please provide valid start and end date.");
			}

			LocalDateTime current = LocalDateTime.now();

			if (end.compareTo(current) > 0 || start.compareTo(current) > 0) {
				throw new InvalidInputException(
						"Invalid request parameters, the dates cannot be in future for historical data retrival.");
			}

		} catch (Exception e) {
			throw e;
		}
		return joinPoint.proceed();
	}

	private LocalDateTime getLocalDateTime(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		return LocalDate.parse(dateString, formatter).atStartOfDay();
	}
}
