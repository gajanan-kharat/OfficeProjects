/*
 * Creation : Nov 4, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.application.typeoftest;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;

/**
 * TypeOfTestService interface.
 *
 * @author mehaj
 */
@Service
public interface TypeOfTestService {

	/**
	 * Delete.
	 *
	 * @param typeId the type id
	 */
	public void delete(long typeId);

	/**
	 * Save type of test.
	 *
	 * @param typeOfTest the type of test
	 * @return the type of test
	 */
	public TypeOfTest saveTypeOfTest(TypeOfTest typeOfTest);
}
