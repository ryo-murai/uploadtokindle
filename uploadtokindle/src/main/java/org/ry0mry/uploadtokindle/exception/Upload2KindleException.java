package org.ry0mry.uploadtokindle.exception;


public class Upload2KindleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int errorCode;

	public Upload2KindleException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public Upload2KindleException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public Upload2KindleException(Throwable cause, int errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
