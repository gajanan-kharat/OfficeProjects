package com.citi.cmb.gce.accountservices.logger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.ext.LoggerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.citi.cmb.gce.accountservices.constant.ASConstant;

public class AccountServiceLogger extends LoggerWrapper {

	private static String logLevel = null;
	
	@Autowired
	private Environment environment;

	public AccountServiceLogger(Logger logger, String fqcn) {
		super(logger, fqcn);
	}

	private boolean isDebugEnabledForApp() {
		if (StringUtils.isBlank(logLevel)) {
			logLevel = environment.getProperty(ASConstant.LOGGER_LEVEL);
		}
		if (ASConstant.DEBUG.equals(logLevel))
			return true;
		return false;
	}
	
	@Override
	public void debug(String msg) {
		if (isDebugEnabledForApp()) {
			super.debug(msg);
		}
	}
	
	@Override
	public void debug(String format, Object arg) {
		if (isDebugEnabledForApp()) {
		super.debug(format, arg);
		}
	}
	
	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		if (isDebugEnabledForApp()) {
		super.debug(marker, format, arg1, arg2);
		}
	}
	
	@Override
	public void debug(String format, Object... argArray) {
		if (isDebugEnabledForApp()) {
		super.debug(format, argArray);
		}
	}
	
	@Override
	public void debug(String msg, Throwable t) {
		if (isDebugEnabledForApp()) {
		super.debug(msg, t);
		}
	}

}
