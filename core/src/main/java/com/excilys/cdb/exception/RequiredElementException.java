package com.excilys.cdb.exception;

public class RequiredElementException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 2052019L;

	public RequiredElementException(String name) {
		super("An element is required: " + name);
	}
}
