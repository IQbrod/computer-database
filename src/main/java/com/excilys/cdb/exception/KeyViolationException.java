package com.excilys.cdb.exception;

public class KeyViolationException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 18042019L;

	public KeyViolationException(int id) {
		super("The provided id ["+id+"] is already used");
	}
}
