/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.checker.formula.services;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class EquationConstants.
 * 
 * @author U224106
 */
public class EquationConstants {

	/** The constants list. */
	private List<String> lConstants = new ArrayList<String>();

	/**
	 * Instantiates a new equation constants.
	 */
	public EquationConstants() {
		setConstants();
	}

	/**
	 * Sets the constants.
	 */
	private void setConstants() {
		lConstants.add("%pi");
		lConstants.add("%e");
	}

	/**
	 * Checks for the constant.
	 * 
	 * @param s
	 *            The string to be analyze
	 * @return true, if it's a constant otherwise false.
	 */
	public boolean isConstant(String s) {
		return lConstants.contains(s.toLowerCase());
	}
}
