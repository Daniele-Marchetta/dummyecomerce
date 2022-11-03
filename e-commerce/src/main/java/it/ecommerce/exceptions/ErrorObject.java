package it.ecommerce.exceptions;

public class ErrorObject {

	private Integer StatusCode;
	
	private long timestamp;

	private String message;

	
	public ErrorObject(Integer statusCode, String message, long timestamp) {
		super();
		StatusCode = statusCode;
		this.message = message;
		this.timestamp = timestamp;
	}

	public ErrorObject() {
		super();
	}

	public Integer getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(Integer statusCode) {
		StatusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
