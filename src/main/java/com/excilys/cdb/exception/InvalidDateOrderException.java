package com.excilys.cdb.exception;


public class InvalidDateOrderException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 18042019L;

	public InvalidDateOrderException(String before, String after) {
		super(after+" must be greater than "+before);
	}
}
