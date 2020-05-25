package com.citi.cmb.gce.accountservices.exception;

public class DataException extends BaseException {

	private static final long serialVersionUID = 1L;

	public DataException() {
		super();
	}

	public DataException(final String errorCode, final String errMsg, final String[] contextData, final Exception expObj) {
		super(errorCode, errMsg, contextData, expObj);
	}
	
}
