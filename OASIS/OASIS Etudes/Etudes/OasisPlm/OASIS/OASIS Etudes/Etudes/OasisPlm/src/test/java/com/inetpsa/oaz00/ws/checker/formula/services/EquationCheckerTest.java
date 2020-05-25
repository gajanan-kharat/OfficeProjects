/*
 * @(#)EquationCheckerTest.java September 23, 2014
 * CopyRight : The PSA Company
 */
package com.inetpsa.oaz00.ws.checker.formula.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;

import com.inetpsa.oaz00.ws.checker.formula.services.EquationChecker;
import com.inetpsa.oaz00.ws.checker.formula.services.EquationException;

import junit.framework.TestCase;

public class EquationCheckerTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Testing that the provided formula and reportFormula are invalid and hence shows INVALID_FORMULA as an errorDescription and rest.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testCheckInvalidFormula1() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInvalidFormula", String.class, String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "* sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2-#UR-0000222#^2) ) ^2+ ( #UR-0031221#/#UR-0031222# ) ^2+#UR-0031222#^2)", "* sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2-UR-0000222^2) ) ^2+ ( UR-0031221/UR-0031222 ) ^2+UR-0031222^2)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(2, ee.getErrorStatus().intValue());
				assertEquals(1, ee.getPosition().intValue());
				assertEquals("Invalid formula", ee.getErrorDescription());
			}
		}
	}

	public void testCheckInvalidFormula2() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInvalidFormula", String.class, String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "? sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2-#UR-0000222#^2) ) ^2+ ( #UR-0031221#/#UR-0031222# ) ^2+#UR-0031222#^2)", "? sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2-UR-0000222^2) ) ^2+ ( UR-0031221/UR-0031222 ) ^2+UR-0031222^2)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(2, ee.getErrorStatus().intValue());
				assertEquals(1, ee.getPosition().intValue());
				assertEquals("Invalid formula", ee.getErrorDescription());
			}
		}
	}

	public void testCheckInvalidFormula3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInvalidFormula", String.class, String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2) ,", "sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2) ,");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(2, ee.getErrorStatus().intValue());
				assertEquals(120, ee.getPosition().intValue());
				assertEquals("Invalid formula", ee.getErrorDescription());
			}
		}
	}

	public void testCheckInvalidFormula4() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInvalidFormula", String.class, String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2) /", "sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2) /");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(2, ee.getErrorStatus().intValue());
				assertEquals(120, ee.getPosition().intValue());
				assertEquals("Invalid formula", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing for the brackets inconsistency. The number of opening brackets is different from the number of closing ones: a distinction is made
	 * between parentheses () and square brackets[].
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testCheckInconsistentBrackets1() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInconsistentBrackets", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "sqrt] ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2]");
		}

		catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(3, ee.getErrorStatus().intValue());
				assertEquals(5, ee.getPosition().intValue());
				assertEquals("Brackets number", ee.getErrorDescription());
			}
		}
	}

	public void testCheckInconsistentBrackets2() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInconsistentBrackets", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2]]");
		}

		catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(3, ee.getErrorStatus().intValue());
				assertEquals(131, ee.getPosition().intValue());
				assertEquals("Brackets number", ee.getErrorDescription());
			}
		}
	}

	public void testCheckInconsistentBrackets3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInconsistentBrackets", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt ( #UR-0000211#*#UR-0000212# ) )^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2]");
		}

		catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(3, ee.getErrorStatus().intValue());
				assertEquals(36, ee.getPosition().intValue());
				assertEquals("Brackets number", ee.getErrorDescription());
			}
		}
	}

	public void testCheckInconsistentBrackets4() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInconsistentBrackets", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# )) ^2+#UR-0031222#^2");
		}

		catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(3, ee.getErrorStatus().intValue());
				assertEquals(113, ee.getPosition().intValue());
				assertEquals("Brackets number", ee.getErrorDescription());
			}
		}
	}

	public void testCheckInconsistentBrackets5() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInconsistentBrackets", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt((#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2]");
		}

		catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(3, ee.getErrorStatus().intValue());
				assertEquals(132, ee.getPosition().intValue());
				assertEquals("Brackets number", ee.getErrorDescription());
			}
		}
	}

	public void testCheckInconsistentBrackets6() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInconsistentBrackets", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) )) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2]");
		}

		catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(3, ee.getErrorStatus().intValue());
				assertEquals(79, ee.getPosition().intValue());
				assertEquals("Brackets number", ee.getErrorDescription());
			}
		}
	}

	public void testCheckInconsistentBrackets7() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkInconsistentBrackets", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222#) ) ^2+#UR-0031222#^2]");
		}

		catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(3, ee.getErrorStatus().intValue());
				assertEquals(113, ee.getPosition().intValue());
				assertEquals("Brackets number", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing that the provided reportFormula may not contains any empty brackets block.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void testCheckEmptyBlock1() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEmptyBlock", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ [] + ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(4, ee.getErrorStatus().intValue());
				assertEquals(41, ee.getPosition().intValue());
				assertEquals("Empty brackets", ee.getErrorDescription());
			}
		}
	}

	public void testCheckEmptyBlock2() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEmptyBlock", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2 + ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2()+#UR-0031222#^2)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(4, ee.getErrorStatus().intValue());
				assertEquals(118, ee.getPosition().intValue());
				assertEquals("Empty brackets", ee.getErrorDescription());
			}
		}
	}

	public void testCheckEmptyBlock3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEmptyBlock", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt() ( #UR-0000211#*#UR-0000212# ) ^2+ [] + ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(4, ee.getErrorStatus().intValue());
				assertEquals(5, ee.getPosition().intValue());
				assertEquals("Empty brackets", ee.getErrorDescription());
			}
		}
	}

	public void testCheckEmptyBlock4() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEmptyBlock", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2 + ( sqrt(#UR-0000221#^2+#UR-0000222#^2()) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(4, ee.getErrorStatus().intValue());
				assertEquals(78, ee.getPosition().intValue());
				assertEquals("Empty brackets", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing string which is followed by a closing bracket. Must be: nothing, an operator or a separator.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testCheckCloseBlock1() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkCloseBlock", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "a+b()s");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(5, ee.getErrorStatus().intValue());
				assertEquals(4, ee.getPosition().intValue());
				assertEquals("Bracket position", ee.getErrorDescription());
			}
		}
	}

	public void testCheckCloseBlock2() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkCloseBlock", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "a + b()s + sin(90)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(5, ee.getErrorStatus().intValue());
				assertEquals(6, ee.getPosition().intValue());
				assertEquals("Bracket position", ee.getErrorDescription());
			}
		}
	}

	public void testCheckCloseBlock3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkCloseBlock", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "sin(90)+cos(0)b");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(5, ee.getErrorStatus().intValue());
				assertEquals(13, ee.getPosition().intValue());
				assertEquals("Bracket position", ee.getErrorDescription());
			}
		}
	}

	public void testCheckCloseBlock4() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkCloseBlock", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "(sin(90)+a*b+c*tan(90)c+d)/100");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(5, ee.getErrorStatus().intValue());
				assertEquals(21, ee.getPosition().intValue());
				assertEquals("Bracket position", ee.getErrorDescription());
			}
		}
	}

	public void testCheckCloseBlock5() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkCloseBlock", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "100/(tan(0)+atanh(90)10+b*a)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(5, ee.getErrorStatus().intValue());
				assertEquals(20, ee.getPosition().intValue());
				assertEquals("Bracket position", ee.getErrorDescription());
			}
		}
	}

	public void testCheckCloseBlock6() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkCloseBlock", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "100*cos(90)+sin(90)/(tan(0)20a+atanh(90)+b*a)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(5, ee.getErrorStatus().intValue());
				assertEquals(26, ee.getPosition().intValue());
				assertEquals("Bracket position", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing string may not followed by two successive operators.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testCheckOperator() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		EquationChecker ec = new EquationChecker();
		Method[] marray = EquationChecker.class.getDeclaredMethods();
		Method m = null;
		for (int i = 0; i < marray.length; i++) {
			if (marray[i].getName().equalsIgnoreCase("checkOperator")) {
				m = marray[i];
				break;
			}
		}
		// Method m = EquationChecker.class.getDeclaredMethod("checkOperator", String.class, int);
		if (m != null) {
			m.setAccessible(true);
		}

		try {
			m.invoke(ec, "12+23+12*+23", 2);
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(6, ee.getErrorStatus().intValue());
				assertEquals(9, ee.getPosition().intValue());
				assertEquals("Operator position", ee.getErrorDescription());
			}
		}

		try {
			m.invoke(ec, "++12*12+23", 0);
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(6, ee.getErrorStatus().intValue());
				assertEquals(1, ee.getPosition().intValue());
				assertEquals("Operator position", ee.getErrorDescription());
			}
		}

		try {
			m.invoke(ec, "sin(90)+12*12+cos(0)+23**", 10);
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(6, ee.getErrorStatus().intValue());
				assertEquals(24, ee.getPosition().intValue());
				assertEquals("Operator position", ee.getErrorDescription());
			}
		}

		try {
			m.invoke(ec, "sin(90)+/12*12+cos(0)+23", 7);
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(6, ee.getErrorStatus().intValue());
				assertEquals(8, ee.getPosition().intValue());
				assertEquals("Operator position", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing operators : Whether present at the end of the string.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testCheckOperators() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkOperators", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "(123.234+32.26)/1231323+");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(6, ee.getErrorStatus().intValue());
				assertEquals(23, ee.getPosition().intValue());
				assertEquals("Operator position", ee.getErrorDescription());
			}
		}
		try {
			m.invoke(ec, "tan(90)/cos(90)/");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(6, ee.getErrorStatus().intValue());
				assertEquals(15, ee.getPosition().intValue());
				assertEquals("Operator position", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing seperetors : If it is followed by two successive seperators.
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testCheckSeparator() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		EquationChecker ec = new EquationChecker();
		Method[] marray = EquationChecker.class.getDeclaredMethods();
		Method m = null;
		for (int i = 0; i < marray.length; i++) {
			if (marray[i].getName().equalsIgnoreCase("checkSeparator")) {
				m = marray[i];
				break;
			}
		}

		if (m != null) {
			m.setAccessible(true);
		}

		try {
			m.invoke(ec, "12+23+12,,23", 8);
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(7, ee.getErrorStatus().intValue());
				assertEquals(9, ee.getPosition().intValue());
				assertEquals("Separator position", ee.getErrorDescription());
			}
		}
		try {
			m.invoke(ec, "sin(90)+12*12,cos(0)+23,;tan(90)", 13);
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(7, ee.getErrorStatus().intValue());
				assertEquals(24, ee.getPosition().intValue());
				assertEquals("Separator position", ee.getErrorDescription());
			}
		}

		try {
			m.invoke(ec, "sin(90)12*12+cos(0)+23;,", 22);
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(7, ee.getErrorStatus().intValue());
				assertEquals(23, ee.getPosition().intValue());
				assertEquals("Separator position", ee.getErrorDescription());
			}
		}
		try {
			m.invoke(ec, "sin(90)+12*12;;cos(0)+23", 13);
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(7, ee.getErrorStatus().intValue());
				assertEquals(14, ee.getPosition().intValue());
				assertEquals("Separator position", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing separators: If (, [ or + just before seperators or / or * just after seperators.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testCheckSeparators1() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkSeparators", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "(123.234+32.26)/,1231323");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(7, ee.getErrorStatus().intValue());
				assertEquals(16, ee.getPosition().intValue());
				assertEquals("Separator position", ee.getErrorDescription());
			}
		}
	}

	public void testCheckSeparators2() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkSeparators", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "1-1.55*E1-A1+ max((2*PI),/sin(3*PI/4*ERTY))+[1,2]");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(7, ee.getErrorStatus().intValue());
				assertEquals(25, ee.getPosition().intValue());
				assertEquals("Separator position", ee.getErrorDescription());
			}
		}
	}

	public void testCheckSeparators3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkSeparators", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "1-1.55*E1-A1+ max((2*PI),sin(3*PI/4*ERTY))+[,1,2]");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(7, ee.getErrorStatus().intValue());
				assertEquals(45, ee.getPosition().intValue());
				assertEquals("Separator position", ee.getErrorDescription());
			}
		}
	}

	public void testCheckSeparators4() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkSeparators", String.class);
		m.setAccessible(true);

		try {
			m.invoke(ec, "cos(90)+sin(0)*E1-A1+ max((2*PI)+,tan(90),sin(3*PI/4*ERTY))+[1,2]");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(7, ee.getErrorStatus().intValue());
				assertEquals(33, ee.getPosition().intValue());
				assertEquals("Separator position", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing that the provided reportFormula is not containing more than one decimal dot in a sequence of numbers.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testCheckNumberFormat1() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkNumberFormat", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "min(#UR-0000211#;+2.0.0;#UR-0000212#-2,0,0)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(10, ee.getErrorStatus().intValue());
				assertEquals(19, ee.getPosition().intValue());
				assertEquals("Invalid number", ee.getErrorDescription());
			}
		}
	}

	public void testCheckNumberFormat2() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkNumberFormat", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt(#UR-0000211#)+2.0.0+#UR-0000212#-2");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(10, ee.getErrorStatus().intValue());
				assertEquals(20, ee.getPosition().intValue());
				assertEquals("Invalid number", ee.getErrorDescription());
			}
		}
	}

	public void testCheckNumberFormat3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkNumberFormat", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt(#UR-0000211#)+2+#UR-0000212#-2+min(#UR-0000211#;+2.0.0;#UR-0000212#-2,0,0)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(10, ee.getErrorStatus().intValue());
				assertEquals(55, ee.getPosition().intValue());
				assertEquals("Invalid number", ee.getErrorDescription());
			}
		}
	}

	public void testCheckNumberFormat4() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkNumberFormat", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "min(#UR-0000211#;#UR-0000212#-2,0.0.0,0)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(10, ee.getErrorStatus().intValue());
				assertEquals(33, ee.getPosition().intValue());
				assertEquals("Invalid number", ee.getErrorDescription());
			}

		}
	}

	public void testCheckNumberFormat5() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkNumberFormat", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt(#UR-0000211#)+2+#UR-0000212#-2.123.a");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(10, ee.getErrorStatus().intValue());
				assertEquals(35, ee.getPosition().intValue());
				assertEquals("Invalid number", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing that the provided reportFormula is not containing any missing operator and seperator.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void testCheckMissingOperatorSeperator1() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkMissingOperatorSeperator", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt(#UR-0000211#)2+#UR-0000212#-2.123");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(8, ee.getErrorStatus().intValue());
				assertEquals(19, ee.getPosition().intValue());
				assertEquals("Missing operator or separator", ee.getErrorDescription());
			}
		}
	}

	public void testCheckMissingOperatorSeperator2() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkMissingOperatorSeperator", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt((01A01-211*01A01-212)2 + (sqrt(01A01-221^2+01A01-222^2))^2 + (01A01-31221+01A01-31222)^2 + 01A01-31222^2)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(8, ee.getErrorStatus().intValue());
				assertEquals(27, ee.getPosition().intValue());
				assertEquals("Missing operator or separator", ee.getErrorDescription());
			}
		}
	}

	public void testCheckMissingOperatorSeperator3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkMissingOperatorSeperator", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt((01A01-211*01A01-212)^2 + 3(01A01-31221+01A01-31222)^2 + 01A01-31222^2)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(8, ee.getErrorStatus().intValue());
				assertEquals(33, ee.getPosition().intValue());
				assertEquals("Missing operator or separator", ee.getErrorDescription());
			}
		}
	}

	public void testCheckMissingOperatorSeperator4() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkMissingOperatorSeperator", String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "max((01A01-211 01A01-212)^2 ; (sqrt(01A01-31221^2+01A01-31222^2))^2 ; (01A01-31221+01A01-31222)^2 ; 01A01-31222)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(8, ee.getErrorStatus().intValue());
				assertEquals(15, ee.getPosition().intValue());
				assertEquals("Missing operator or separator", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing that the provided formula and reportFormula are not containing any invalid function.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void testCheckFunction1() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkFunction", String.class, String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "Sqrt #UR-0000211#", "Sqrt 01A01-211");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(9, ee.getErrorStatus().intValue());
				assertEquals(1, ee.getPosition().intValue());
				assertEquals("Invalid function", ee.getErrorDescription());
			}
		}
	}

	public void testCheckFunction2() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkFunction", String.class, String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "min(01A01-211)", "min(#UR-0000211#)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(9, ee.getErrorStatus().intValue());
				assertEquals(1, ee.getPosition().intValue());
				assertEquals("Invalid function", ee.getErrorDescription());
			}
		}
	}

	public void testCheckFunction3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkFunction", String.class, String.class);
		m.setAccessible(true);
		try {
			m.invoke(ec, "sqrt((#UR-0000211#*#UR-0000212# ) ^2+ ( sqrt[#UR-0000221#^2+#UR-0000222#^2] ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)", "sqrt((01A01-211*01A01-212 ) ^2+ ( sqrt[01A01-221^2+01A01-222^2] ) ^2+ ( 01A01-31221+01A01-31222 ) ^2+01A01-31222^2)");
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof EquationException) {
				EquationException ee = (EquationException) ite.getCause();
				assertEquals(9, ee.getErrorStatus().intValue());
				assertEquals(1, ee.getPosition().intValue());
				assertEquals("Invalid function", ee.getErrorDescription());
			}
		}
	}

	/**
	 * Testing that the provided formula and reportFormula are correct and hence returns FORMULA_OK value i.e. (value = 0).
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void testCheckEquation1() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEquation", String.class, String.class);
		m.setAccessible(true);
		String formula = "max( #UR-0000211# ; #UR-0000212# )";
		String reportFormula = "max( UR-0000211 ; UR-0000212 )";
		assertEquals(new BigInteger("0"), m.invoke(ec, formula, reportFormula));
	}

	public void testCheckEquation2() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEquation", String.class, String.class);
		m.setAccessible(true);
		String formula = "+ sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
		String reportFormula = "+ sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2)";
		assertEquals(new BigInteger("0"), m.invoke(ec, formula, reportFormula));
	}

	public void testCheckEquation3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEquation", String.class, String.class);
		m.setAccessible(true);
		String formula = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
		String reportFormula = "sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2)";
		assertEquals(new BigInteger("0"), m.invoke(ec, formula, reportFormula));
	}

	public void testCheckEquation4() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEquation", String.class, String.class);
		m.setAccessible(true);
		String formula = "sqrt( #UR-0000211#*#UR-0000212# ^2 * 20) + sqrt(#UR-0000221#^2+#UR-0000222#^2) ^2+  (#UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2";
		String reportFormula = "sqrt( UR-0000211*UR-0000212 ^2 * 20) + sqrt(UR-0000221^2+UR-0000222^2) ^2+  (UR-0031221+UR-0031222 ) ^2+UR-0031222^2";
		assertEquals(new BigInteger("0"), m.invoke(ec, formula, reportFormula));
	}

	public void testCheckEquation5() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEquation", String.class, String.class);
		m.setAccessible(true);
		String formula = "min(#UR-0000211#;+200;#UR-0000212# - 2,0,0)";
		String reportFormula = "min(UR-0000211;+200;UR-0000212 - 2,0,0)";
		assertEquals(new BigInteger("0"), m.invoke(ec, formula, reportFormula));
	}

	public void testCheckEquation6() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEquation", String.class, String.class);
		m.setAccessible(true);
		String formula = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
		String reportFormula = "sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2)";
		assertEquals(new BigInteger("0"), m.invoke(ec, formula, reportFormula));
	}

	public void testCheckEquation7() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		EquationChecker ec = new EquationChecker();
		Method m = EquationChecker.class.getDeclaredMethod("checkEquation", String.class, String.class);
		m.setAccessible(true);
		String formula = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
		String reportFormula = "sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2)";
		assertEquals(new BigInteger("0"), m.invoke(ec, formula, reportFormula));
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
