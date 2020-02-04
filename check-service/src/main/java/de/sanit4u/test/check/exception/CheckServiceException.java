package de.sanit4u.test.check.exception;

public class CheckServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckServiceException() {
		super();
	}

	public CheckServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CheckServiceException(final String message) {
		super(message);
	}

	public CheckServiceException(final Throwable cause) {
		super(cause);
	}
}