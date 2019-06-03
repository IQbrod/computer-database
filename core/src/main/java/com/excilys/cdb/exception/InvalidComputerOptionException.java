package com.excilys.cdb.exception;

public class InvalidComputerOptionException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 17042019L;

	public InvalidComputerOptionException(String opt) {
		super("Unknown Option: "+opt);
	}
}
