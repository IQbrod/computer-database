package com.excilys.cdb.exception;

public class FailedSQLQueryBySQLException extends ShouldOnlyBeLoggedException {
	private static final long serialVersionUID = 19042019L;

	public FailedSQLQueryBySQLException(String query) {
		super("["+query+"] failed due to an internal SQL error");
	}
}
