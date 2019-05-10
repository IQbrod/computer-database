package com.excilys.cdb.exception;

public class FailedSQLQueryException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 19042019L;

	public FailedSQLQueryException(String query) {
		super("["+query+"] failed");
	}
}
