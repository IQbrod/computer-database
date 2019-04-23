package com.excilys.cdb.exception;

public class InvalidIntegerException extends Exception {
	private static final long serialVersionUID = 17042019L;

	public InvalidIntegerException(String id) {
		super("Invalid Format (NaN): "+id);
	}
}
