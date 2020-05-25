package com.citi.cmb.gce.accountservices.exception;

public class AccountServicesException extends BaseException {

	private static final long serialVersionUID = 1L;

	public AccountServicesException(final String errorCode, final String errMsg, final String[] contextData,
			final Exception expObj) {
		super(errorCode, errMsg, contextData, expObj);
	}
}
