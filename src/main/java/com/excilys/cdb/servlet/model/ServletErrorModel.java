package com.excilys.cdb.servlet.model;

public class ServletErrorModel {
	private int errorCode;
	private String message;
	private String customMessage;
	private String className;

	public ServletErrorModel(int errorCode, String errorMessage, String errorCustomMessage, String className) {
		this.errorCode = errorCode;
		this.message = errorMessage;
		this.customMessage = errorCustomMessage;
		this.className = className;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
