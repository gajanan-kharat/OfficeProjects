/*
 * Creation : Nov 4, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.application.testnature;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.testnature.TestNature;

/**
 * Service class for TestNature.
 *
 * @author mehaj
 */
@Service
public interface TestNatureService {

	/**
	 * Save test nature.
	 *
	 * @param testnature the testnature
	 * @return the test nature
	 */
	public TestNature saveTestNature(TestNature testnature);

	/**
	 * Delete test nature.
	 *
	 * @param testId the test id
	 */
	void deleteTestNature(long testId);

	/**
	 * Update test nature.
	 *
	 * @param testnature the testnature
	 * @return the test nature
	 */
	String updateTestNature(TestNature testnature);
}
