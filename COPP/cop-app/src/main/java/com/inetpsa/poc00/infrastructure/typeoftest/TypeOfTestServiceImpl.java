/*
 * Creation : Nov 4, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.typeoftest;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.typeoftest.TypeOfTestService;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository;

/**
 * TODO : Description.
 *
 * @author mehaj
 */
public class TypeOfTestServiceImpl implements TypeOfTestService {

	/** The type of test repository. */
	@Inject
	TypeOfTestRepository typeOfTestRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.typeoftest.TypeOfTestService#delete(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void delete(long typeId) {
		typeOfTestRepository.deleteTypeOfTest(typeId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.typeoftest.TypeOfTestService#saveTypeOfTest(com.inetpsa.poc00.domain.typeoftest.TypeOfTest)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TypeOfTest saveTypeOfTest(TypeOfTest typeOfTest) {
		TypeOfTest typeOfTestNew = typeOfTestRepository.saveTypeOfTest(typeOfTest);
		return typeOfTestNew;
	}

}
