package com.excilys.cdb.exception;

public class KeyViolationException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 18042019L;

	public KeyViolationException(long l) {
		super("The provided id ["+l+"] is already used");
	}
}
