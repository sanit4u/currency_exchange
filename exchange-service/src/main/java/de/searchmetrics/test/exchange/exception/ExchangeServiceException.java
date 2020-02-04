package de.searchmetrics.test.exchange.exception;

public class ExchangeServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExchangeServiceException() {
		super();
	}

	public ExchangeServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ExchangeServiceException(final String message) {
		super(message);
	}

	public ExchangeServiceException(final Throwable cause) {
		super(cause);
	}
}
