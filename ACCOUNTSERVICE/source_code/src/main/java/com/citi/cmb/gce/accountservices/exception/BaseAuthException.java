package com.citi.cmb.gce.accountservices.exception;

public class BaseAuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private boolean isExcepLogged = false;

	private String errorCode;

	private String errorMsg;

	private Exception expObj;

	private String[] contextData;

	public BaseAuthException(final String errorCode, final String errorMsg, final Exception expObj,
			final String[] contextData) {
		super(errorMsg, expObj);
		setErrorCode(errorCode);
		setErrorMsg(errorMsg);
		setContextData(contextData);
	}

	public boolean isExcepLogged() {
		return this.isExcepLogged;
	}

	public void setExcepLogged(boolean isExcepLogged) {
		this.isExcepLogged = isExcepLogged;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(final String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(final String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Exception getExpObj() {
		return this.expObj;
	}

	public void setExpObj(final Exception expObj) {
		this.expObj = expObj;
	}

	public String[] getContextData() {
		return this.contextData;
	}

	public void setContextData(final String[] contextData) {
		this.contextData = contextData;
	}

}
