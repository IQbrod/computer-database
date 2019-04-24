package com.excilys.cdb.exception;

public class FailedSQLQueryBySQLException extends Exception {
	private static final long serialVersionUID = 19042019L;

	public FailedSQLQueryBySQLException(String query) {
		super("["+query+"] failed");
	}
}
