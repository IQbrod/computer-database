package com.excilys.cdb.exception;

public class FailedCreationException extends Exception {
	private static final long serialVersionUID = 18042019L;

	public FailedCreationException() {
		// SQL Query resulted by no row created => This Exception should never be thrown
		super("Your command failed :/");
	}
}
