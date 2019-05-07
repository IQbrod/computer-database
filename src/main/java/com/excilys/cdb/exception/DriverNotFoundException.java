package com.excilys.cdb.exception;

public class DriverNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 18042019L;

	public DriverNotFoundException(String driver) {
		super("Impossible to find driver: " + driver);
	}
}
