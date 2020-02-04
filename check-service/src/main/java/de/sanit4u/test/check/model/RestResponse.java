package de.sanit4u.test.check.model;

public class RestResponse<T> {
	private int code;
	private String message;
	private T results;

	public RestResponse() {
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the data
	 */
	public T getResults() {
		return results;
	}

	/**
	 * @return the data
	 */
	public void setResults(T results) {
		this.results = results;
	}

	public static <T> RestResponse<T> create(int code, final String message, final T data) {
		RestResponse<T> response = new RestResponse<T>();
		response.setCode(code);
		response.setMessage(message);
		response.setResults(data);

		return response;
	}

	public static <T> RestResponse<T> create(int code, final String message) {

		return create(code, message, null);
	}
}