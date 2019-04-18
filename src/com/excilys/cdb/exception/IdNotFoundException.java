package com.excilys.cdb.exception;

public class IdNotFoundException extends Exception {
	private static final long serialVersionUID = 18042019L;

	public IdNotFoundException(String table, String id) {
		super("No "+table+" found for id: "+id);		
	}
}
