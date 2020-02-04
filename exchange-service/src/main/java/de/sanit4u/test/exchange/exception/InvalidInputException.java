package de.sanit4u.test.exchange.exception;

public class InvalidInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputException() {
		super();
	}

	public InvalidInputException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(final String message) {
		super(message);
	}

	public InvalidInputException(final Throwable cause) {
		super(cause);
	}
}
