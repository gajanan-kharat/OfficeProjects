package com.citi.cmb.gce.accountservices.exception;

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	private boolean isExcepLogged = false;
	
	private String errorCode;
	
	private String errMsg;
	
	private String[] contextData;
	
	private Exception expObj;

	public BaseException() {
		super();
	}
	
	public BaseException(final String errorCode, final String errMsg, final String[] contextData,
			final Exception expObj) {
		super(errMsg,expObj);
		setErrorCode(errorCode);
		setErrMsg(errMsg);
		setContextData(contextData);
		setExpObj(expObj);
	}

	public boolean isExcepLogged() {
		return isExcepLogged;
	}

	public void setExcepLogged(final boolean isExcepLogged) {
		this.isExcepLogged = isExcepLogged;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String[] getContextData() {
		return contextData;
	}

	public void setContextData(String[] contextData) {
		this.contextData = contextData;
	}

	public Exception getExpObj() {
		return expObj;
	}

	public void setExpObj(Exception expObj) {
		this.expObj = expObj;
	}
	
}
