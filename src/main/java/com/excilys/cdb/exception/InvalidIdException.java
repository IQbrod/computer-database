package com.excilys.cdb.exception;

public class InvalidIdException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 17042019L;

	public InvalidIdException(int id) {
		super("Invalid id: "+id);
	}
}
