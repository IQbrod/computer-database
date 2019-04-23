package com.excilys.cdb.exception;

public class ForeignKeyViolationException extends Exception {
	private static final long serialVersionUID = 18042019L;

	public ForeignKeyViolationException(int id, String table) {
		super("The provided id ["+id+"] does not exists in table ["+table+"]");
	}
}
