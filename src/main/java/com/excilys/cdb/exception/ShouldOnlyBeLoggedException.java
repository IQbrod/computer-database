package com.excilys.cdb.exception;

public abstract class ShouldOnlyBeLoggedException extends RuntimeException {
	private static final long serialVersionUID = 9052019L;
	
	public ShouldOnlyBeLoggedException(String msg) {
		super(msg);
	}
}
