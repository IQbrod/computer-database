package com.excilys.cdb.exception;

public class UnknownCommandException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 17042019L;

	public UnknownCommandException(String ukcmd) {
		super("Unknown Command: ["+ukcmd+"]");
	}
}
