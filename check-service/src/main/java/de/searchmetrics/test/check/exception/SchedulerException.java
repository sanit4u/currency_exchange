package de.searchmetrics.test.check.exception;

public class SchedulerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SchedulerException() {
		super();
	}

	public SchedulerException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SchedulerException(final String message) {
		super(message);
	}

	public SchedulerException(final Throwable cause) {
		super(cause);
	}
}