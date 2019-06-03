package com.excilys.cdb.controller.model;

public class ServletErrorModel {
	private int errorCode;
	private String customMessage;
	private String className;

	public ServletErrorModel(int errorCode, String errorCustomMessage, String className) {
		this.errorCode = errorCode;
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
	public String getCustomMessage() {
		return customMessage;
	}
	public void setCustomMessage(String errorCustomMessage) {
		this.customMessage = errorCustomMessage;
	}
	
	
}
