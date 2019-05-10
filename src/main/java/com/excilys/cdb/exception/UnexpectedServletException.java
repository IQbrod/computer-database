package com.excilys.cdb.exception;

public class UnexpectedServletException extends ShouldOnlyBeLoggedException {
	private static final long serialVersionUID = 9052019L;
	
	public UnexpectedServletException(String servletName, String method) {
		super("Something went wrong with the servlet: "+ servletName +" on method "+ method);
	}
}
