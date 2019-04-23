package com.excilys.cdb.exception;

import java.sql.Timestamp;

public class InvalidDateOrderException extends RuntimeException {
	private static final long serialVersionUID = 18042019L;

	public InvalidDateOrderException(Timestamp before, Timestamp after) {
		super(after+" must be greater than "+before);
	}
}
