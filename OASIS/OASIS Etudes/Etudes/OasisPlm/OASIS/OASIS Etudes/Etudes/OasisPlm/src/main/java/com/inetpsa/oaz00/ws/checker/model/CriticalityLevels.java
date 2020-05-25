/*
 * @(#)CriticalityLevels.java October 01, 2014
 * CopyRight : The PSA Company
 */
package com.inetpsa.oaz00.ws.checker.model;

/**
 * The Enum CriticalityLevels.
 * 
 * @author Geometric Ltd.
 * 
 */
public enum CriticalityLevels {
	/** CriticalityLevels information */
	INFORMATION("Information"),
	/** CriticalityLevels warning */
	WARNING("Warning"),
	/** CriticalityLevels error. */
	ERROR("Error");

	/** CriticalityLevels value. */
	private final String criticalityValue;

	/**
	 * Instantiates a new CriticalityLevels.
	 * 
	 * @param criticalityValue
	 *            The value.
	 */
	CriticalityLevels(String criticalityValue) {
		this.criticalityValue = criticalityValue;
	}

	/**
	 * The Criticality levels value.
	 * 
	 * @return String.
	 */
	public String criticalityValue() {
		return criticalityValue;
	}

}
