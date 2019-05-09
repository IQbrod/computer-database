package com.excilys.cdb.exception;

public class UnableToSendErrorCodeException extends Exception {
	private static final long serialVersionUID = 9052019L;
	
	public UnableToSendErrorCodeException(String servletName, String method) {
		super("Could not send http status: @"+ servletName +":"+ method);
	}
}
