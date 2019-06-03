package com.excilys.cdb.exception;

public class InvalidDateFormatException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 18042019L;

	public InvalidDateFormatException(String format, String date) {
		super("Invalid date format ("+format+"): "+date);
	}
}
