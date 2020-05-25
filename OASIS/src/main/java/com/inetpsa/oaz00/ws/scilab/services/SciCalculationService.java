/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.scilab.services;

/**
 * The Interface SciCalculationService.
 * 
 * @author U224106
 */
public interface SciCalculationService {

	/**
	 * Send to SCILAB.
	 */
	public void sendToScilab();

	/**
	 * Compute in SCILAB.
	 */
	public void computeInScilab();

	/**
	 * Gets the Scilab data.
	 * 
	 * @return the SCILAB data
	 */
	public double[] getScilabData();

}
