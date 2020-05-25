/*
 * @(#)FunctionalLogTest.java October 10, 2014
 * CopyRight : The PSA Company
 */
package com.inetpsa.oaz.ws.scilab.utils;

import java.io.File;
import java.util.Calendar;

import junit.framework.TestCase;

import org.junit.Test;

import com.inetpsa.oaz00.ws.scilab.utils.FunctionalLog;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationType;

/**
 * 
 * Test for checking the logFile creation.
 * 
 * @author Geometric Ltd.
 * 
 */
public class FunctionalLogTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * check for logFile created or not.
	 * 
	 * @throws SecurityException
	 */
	@Test
	public void testwriteLogDetails() throws SecurityException {
		Calendar now = Calendar.getInstance();
		// Get the start_Date for the request.
		String start_Date = now.get(Calendar.YEAR) + "." + now.get(Calendar.DAY_OF_YEAR) + "." + now.get(Calendar.HOUR_OF_DAY) + "." + now.get(Calendar.MINUTE);
		String userId = "sagargu";
		CalculationType module = CalculationType.MONTE_CARLO;
		String projectName = "PSA Geometric Test";

		File logFile = FunctionalLog.writeLogDetails(start_Date, projectName, userId, module.toString());

		assertNotNull(logFile);
		// TODO: add more tests pertaining to content of file.
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

}
