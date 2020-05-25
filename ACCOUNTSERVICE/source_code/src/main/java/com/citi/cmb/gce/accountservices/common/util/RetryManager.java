package com.citi.cmb.gce.accountservices.common.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
@Lazy(value = true)
public class RetryManager {
	public static final int DEFAULT_RETRIES = 3;
	public static final long DEFAULT_WAIT_TIME = 1;
	public static final String DEFAULT_RETRIED_METHOD = "";

	private int numberOfRetries;
	private int numberOfTriesLeft;
	private long timeToWait;
	private String retriedMethodName;

	public RetryManager() {
		this(DEFAULT_RETRIES, DEFAULT_WAIT_TIME, DEFAULT_RETRIED_METHOD);
	}

	public RetryManager(int numberOfRetries, long timeToWait, String retriedMethodName) {
		this.numberOfRetries = numberOfRetries;
		numberOfTriesLeft = numberOfTriesLeft;
		this.timeToWait = timeToWait * 1000;
		this.retriedMethodName = retriedMethodName;
	}
	
	public boolean shouldRetry() {
		return numberOfRetries>0;
	}
	
	public void errorOccurred() throws Exception {
		numberOfTriesLeft--;
		if(!shouldRetry()) {
		throw new Exception("Retry Failed for method:"+this.retriedMethodName+ " Total:"+numberOfRetries+
				" attems made at interval: "+getTimeToWait()
				+"ms");
	}
		waitUntilNextTry();
	}

	private void waitUntilNextTry() {

		try {
			Thread.sleep(getTimeToWait());
		} catch (InterruptedException ignored) {
			Thread.currentThread().interrupt();
		}
	}

	private long getTimeToWait() {
		return this.timeToWait;
	}

	public int getNumberOfTriesLeft() {
		return numberOfTriesLeft;
	}
	public void setNumberOfTriesLeft(int numberOfTriesLeft) {
		this.numberOfTriesLeft = numberOfTriesLeft;
	}
}
