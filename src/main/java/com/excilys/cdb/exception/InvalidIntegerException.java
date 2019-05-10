package com.excilys.cdb.exception;

public class InvalidIntegerException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 17042019L;

	public InvalidIntegerException(String id) {
		super("Invalid Format (NaN): "+id);
	}
}
