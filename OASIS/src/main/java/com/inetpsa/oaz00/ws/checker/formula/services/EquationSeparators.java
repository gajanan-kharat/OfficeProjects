/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.checker.formula.services;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class EquationSeparators.
 * 
 * @author U224106
 */
public class EquationSeparators {

	/** The separators list. */
	private List<String> lSeparators = new ArrayList<String>();

	/**
	 * Instantiates a new equation separators.
	 */
	public EquationSeparators() {
		setSeparators();
	}

	/**
	 * Sets the separators.
	 */
	private void setSeparators() {
		lSeparators.add(",");
		lSeparators.add(";");
	}

	/**
	 * Checks if is a separators.
	 * 
	 * @param s
	 *            the string to be analyze.
	 * @return true, if is a separators
	 */
	public boolean isSeparators(String s) {
		return lSeparators.contains(s);
	}

	/**
	 * Gets the separators.
	 * 
	 * @return the separators
	 */
	public List<String> getSeparators() {
		return lSeparators;
	}
}
