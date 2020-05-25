package com.citi.cmb.gce.accountservices.exception;

public class APIAuthenticationException extends BaseAuthException {

	private static final long serialVersionUID = 1L;

	public APIAuthenticationException(final String errorCode, final String errorMsg, final Exception expObj, final String[] contextData) {
		super(errorCode, errorMsg, expObj, contextData);
	}
}
