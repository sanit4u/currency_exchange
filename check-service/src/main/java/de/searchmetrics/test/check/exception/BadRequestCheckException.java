package de.searchmetrics.test.check.exception;

public class BadRequestCheckException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestCheckException() {
		super();
	}

	public BadRequestCheckException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BadRequestCheckException(final String message) {
		super(message);
	}

	public BadRequestCheckException(final Throwable cause) {
		super(cause);
	}
}