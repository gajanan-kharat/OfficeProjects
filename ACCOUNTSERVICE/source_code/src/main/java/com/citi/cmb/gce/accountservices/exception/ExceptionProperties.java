package com.citi.cmb.gce.accountservices.exception;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@PropertySource("classpath:exception-message.properties")
@ConfigurationProperties(prefix="accountservices")
public class ExceptionProperties {

	private Map<String, String> errorCodes;

	public Map<String, String> getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(Map<String, String> errorCodes) {
		this.errorCodes = errorCodes;
	}
	
	public String getErrorMessage(String errorCode) {
		return errorCodes.get(errorCode);
	}
}
