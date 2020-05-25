package com.citi.cmb.gce.accountservices.exception;

import java.util.List;
import java.util.Map;

public class ExceptionResponse {
	
	private String errorCode;
	private String errorMessage;
	private String exception;
	private List<String> errors;
	private Map<String, String> errorMap;
	private String [] runtimeData;
	private boolean runtimeDataRequired;
	private String dbMessage;
	
	public ExceptionResponse() {
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}

	public String[] getRuntimeData() {
		return runtimeData;
	}

	public void setRuntimeData(String[] runtimeData) {
		this.runtimeData = runtimeData;
	}

	public boolean isRuntimeDataRequired() {
		return runtimeDataRequired;
	}

	public void setRuntimeDataRequired(boolean runtimeDataRequired) {
		this.runtimeDataRequired = runtimeDataRequired;
	}

	public String getDbMessage() {
		return dbMessage;
	}

	public void setDbMessage(String dbMessage) {
		this.dbMessage = dbMessage;
	}

}
