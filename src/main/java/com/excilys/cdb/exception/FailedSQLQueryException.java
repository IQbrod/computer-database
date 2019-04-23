package com.excilys.cdb.exception;

public class FailedSQLQueryException extends Exception {
	private static final long serialVersionUID = 19042019L;

	public FailedSQLQueryException(String query) {
		super("["+query+"] failed");
	}
}
