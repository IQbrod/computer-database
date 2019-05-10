package com.excilys.cdb.exception;

public abstract class ShouldBeSentToClientException extends RuntimeException {
	private static final long serialVersionUID = 9052019L;
	
	public ShouldBeSentToClientException(String msg) {
		super(msg);
	}
}
