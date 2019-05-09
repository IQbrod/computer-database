package com.excilys.cdb.exception;

public class UnableToSendErrorCodeException extends Exception {
	private static final long serialVersionUID = 9052019L;
	
	public UnableToSendErrorCodeException(int code) {
		super("Could not send http status: "+ code);
	}
}
