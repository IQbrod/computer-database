package com.excilys.cdb.exception;

public class MissingArgumentException extends ShouldBeSentToClientException {
	private static final long serialVersionUID = 17042019L;

	public MissingArgumentException(int exp, int rcv) {
		super("Missing Argument: expected:"+exp+" received:"+rcv);
	}
}
