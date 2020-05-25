/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.checker.formula.services;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class EquationFunctions.
 * 
 * @author U224106
 */
public class EquationFunctions {

	/** The functions list. */
	private List<String> lFunctions = new ArrayList<String>();

	/**
	 * Instantiates a new equation functions.
	 */
	public EquationFunctions() {
		setFunctions();
	}

	/**
	 * Sets the functions.
	 */
	private void setFunctions() {
		lFunctions.add("abs");
		lFunctions.add("acos");
		lFunctions.add("acosh");
		lFunctions.add("asin");
		lFunctions.add("asinh");
		lFunctions.add("atan");
		lFunctions.add("atanh");
		lFunctions.add("cos");
		lFunctions.add("cosh");
		lFunctions.add("ceil");
		lFunctions.add("round");
		lFunctions.add("floor");
		lFunctions.add("fix");
		lFunctions.add("exp");
		lFunctions.add("int");
		lFunctions.add("log");
		lFunctions.add("log10");
		lFunctions.add("min");
		lFunctions.add("max");
		lFunctions.add("modulo");
		lFunctions.add("sin");
		lFunctions.add("sinh");
		lFunctions.add("sqrt");
		lFunctions.add("tan");
		lFunctions.add("tanh");
	}

	/**
	 * Checks if the String is a function or not.
	 * 
	 * @param s
	 *            the string to be analyze.
	 * @return true, if is a function
	 */
	public boolean isFunction(String s) {
		return lFunctions.contains(s.toLowerCase());
	}
}
