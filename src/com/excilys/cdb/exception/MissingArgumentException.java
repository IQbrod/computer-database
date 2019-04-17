package com.excilys.cdb.exception;

public class MissingArgumentException extends Exception {
	private static final long serialVersionUID = 17042019L;

	public MissingArgumentException(String cmd, String missingArg) {
		super("Missing Argument: "+cmd+"<"+missingArg+">");
	}
}
