package com.excilys.cdb.exception;

public class InvalidTableException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 17042019L;

	public InvalidTableException(String invTab) {
		super("Invalid table name: "+invTab);
	}
}
