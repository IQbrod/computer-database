package com.excilys.cdb.servlet.model;

public class ServletErrorModel {
	private int errorCode;
	private String message;
	private String customMessage;
	
	public ServletErrorModel(int errorCode, String errorMessage, String errorCustomMessage) {
		this.errorCode = errorCode;
		this.message = errorMessage;
		this.customMessage = errorCustomMessage;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String errorMessage) {
		this.message = errorMessage;
	}
	public String getCustomMessage() {
		return customMessage;
	}
	public void setCustomMessage(String errorCustomMessage) {
		this.customMessage = errorCustomMessage;
	}
	
	
}
