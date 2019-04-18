package com.excilys.cdb.exception;

public class InvalidDateValueException extends Exception {
	private static final long serialVersionUID = 18042019L;

	public InvalidDateValueException(String dateValue) {
		super("Invalid date value: "+dateValue);
	}
}
