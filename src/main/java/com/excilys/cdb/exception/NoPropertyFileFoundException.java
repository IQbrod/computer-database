package com.excilys.cdb.exception;

public class NoPropertyFileFoundException extends RuntimeException {
	private static final long serialVersionUID = 2052019L;

	public NoPropertyFileFoundException(String file) {
		super("No configuration file found: " + file);
	}
}
