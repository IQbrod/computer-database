package com.excilys.cdb.exception;

public class UnexpectedServletException extends Exception {
	private static final long serialVersionUID = 9052019L;
	
	public UnexpectedServletException(String servletName, String method) {
		super("Something went wring with the servlet: "+ servletName +" on method "+ method);
	}
}
