package com.excilys.cdb.exception;

public class FailedSQLQueryException extends ShouldOnlyBeLoggedException {
	private static final long serialVersionUID = 19042019L;

	public FailedSQLQueryException(String query) {
		super("["+query+"] failed");
	}
}
