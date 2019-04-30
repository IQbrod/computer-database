package com.excilys.cdb.exception;

public class PrimaryKeyViolationException extends RuntimeException {
	private static final long serialVersionUID = 18042019L;

	public PrimaryKeyViolationException(int id) {
		super("The provided id ["+id+"] is already used");
	}
}
