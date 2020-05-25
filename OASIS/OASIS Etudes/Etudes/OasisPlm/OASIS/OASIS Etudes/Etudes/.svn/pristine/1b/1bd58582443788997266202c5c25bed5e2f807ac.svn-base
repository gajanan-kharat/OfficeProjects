/*
 * @(#)ParametersCheckerTest.java November 27, 2014
 * CopyRight : The PSA Company
 */
package com.inetpsa.oaz00.ws.checker.formula.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.inetpsa.oaz00.ws.checker.formula.services.ParametersChecker;

import junit.framework.TestCase;

/**
 * 
 * @author Geometric Ltd.
 *
 */
public class ParametersCheckerTest extends TestCase {
	
	private ParametersChecker pc;
	private Method theMethod;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		pc = new ParametersChecker();
		Method[] theMethods = ParametersChecker.class.getDeclaredMethods();
		for (Method method : theMethods) {
			if (method.getName().equals("getIndexOfErrorFromReportFormula")) {
				theMethod = method;
				theMethod.setAccessible(true);
				break;
			}
		}
	}
	
	/**
	 * Test getIndexOfErrorFromReportFormula 1
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testGetIndexOfErrorFromReportFormula1() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		String cf = "-1.20 + sqrt( ( #UR-0000211#*#UR-0000212#  ^2 )(sqrt(#UR-0000221#^2+#UR-0000222#^2)) ) ^2+ ( (#UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
		String rf = "-1.20 + sqrt( ( A*B  ^2 )(sqrt(C^2+D^2)) ) ^2+ ( (E+F) ^2+G^2)";
		String var = "#UR-0000211#";
		// Check the expected and actual error position.
		assertEquals(16, theMethod.invoke(this.pc, var, cf, rf));
	}
	
	/**
	 * Test getIndexOfErrorFromReportFormula 2
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testGetIndexOfErrorFromReportFormula2() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		String cf = "sqrt( ( #UR-0000211#*#UR-0000212#  ^2 )(sqrt(#UR-0000221#^2+#UR-0000222#^2)) )";
		String rf = "sqrt( ( A*B  ^2 )(sqrt(C^2+D^2)) )";
		String var = "#UR-0000222#";
		// Check the expected and actual error position.
		assertEquals(27, theMethod.invoke(this.pc, var, cf, rf));
	}
	
	/**
	 * Test getIndexOfErrorFromReportFormula 3
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testGetIndexOfErrorFromReportFormula3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		String cf = "0.1 + sqrt( ( #UR-0000211#*#UR-0000212#  ^2 )(sqrt(#UR-0000221#^2+#UR-0000222#^2)) ) ^2+ ( (#UR-0031221#+#UR-0031222# ) )";
		String rf = "0.1 + sqrt( ( A*B  ^2 )(sqrt(C^2+D^2)) ) ^2+ ( (E+F) )";
		String var = "#UR-0031222#";
		// Check the expected and actual error position.
		assertEquals(50, theMethod.invoke(this.pc, var, cf, rf));
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		pc = null;
		theMethod = null;
	}

}
