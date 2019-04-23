package com.excilys.cdb.exception;

public class InvalidPageSizeException extends Exception {
	private static final long serialVersionUID = 18042019L;

	public InvalidPageSizeException(int s) {
		super("Invalid Page Size: "+s);
	}
}
