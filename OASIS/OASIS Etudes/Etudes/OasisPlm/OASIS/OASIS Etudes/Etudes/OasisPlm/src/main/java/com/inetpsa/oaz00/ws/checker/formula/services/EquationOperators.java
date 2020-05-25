/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.checker.formula.services;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class EquationOperators.
 * 
 * @author U224106
 */
public class EquationOperators {

	/** The operators list. */
	private List<String> lOperators = new ArrayList<String>();

	/**
	 * Instantiates a new equation operators.
	 */
	public EquationOperators() {
		setOperators();
	}

	/**
	 * Sets the operators.
	 */
	private void setOperators() {
		lOperators.add("*");
		lOperators.add("/");
		lOperators.add("+");
		lOperators.add("-");
		lOperators.add("^");
	}

	/**
	 * Checks if is an operator.
	 * 
	 * @param s
	 *            the string to be analyze.
	 * @return true, if is an operator
	 */
	public boolean isOperator(String s) {
		return lOperators.contains(s);
	}
}
