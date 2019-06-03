package com.excilys.cdb.exception;

public class DatabaseProblemException extends ShouldOnlyBeLoggedException {
	private static final long serialVersionUID = 18042019L;

	public DatabaseProblemException(String db, String user, String pass) {
		super("Could not connect at "+db+" as "+user+": "+pass);
	}
}
