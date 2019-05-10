package com.excilys.cdb.exception;

public class InvalidPageValueException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 18042019L;

	public InvalidPageValueException(int v) {
		super("Invalid Page Value: "+v);
	}
}
