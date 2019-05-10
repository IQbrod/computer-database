package com.excilys.cdb.exception;

public class TooManyArgumentsException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 17042019L;

	public TooManyArgumentsException(String arg) {
		super("Too many arguments: "+arg+"...");
	}
}
