/*
 * Creation : Sep 26, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.domain.typeoftest;

import org.seedstack.business.domain.GenericRepository;

/**
 * the TypeOfTestRepository file.
 *
 * @author mehaj
 */
public interface TypeOfTestRepository extends GenericRepository<TypeOfTest, Long> {

	/**
	 * Saves the TypeOfTest.
	 * 
	 * @param object the TypeOfTest to save
	 * @return the TypeOfTest
	 */
	TypeOfTest saveTypeOfTest(TypeOfTest object);

	/**
	 * Delete TypeOfTest.
	 *
	 * @param typeOfTestId the type of test id
	 * @return the long TypeOfTestid
	 */
	long deleteTypeOfTest(Long typeOfTestId);

	/**
	 * Load type of test.
	 *
	 * @param typeOfTestId the type of test id
	 * @return the type of test
	 */
	TypeOfTest loadTypeOfTest(Long typeOfTestId);
}