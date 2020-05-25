/*
 * Creation : Sep 26, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.jpa.typeoftest;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository;

/**
 * the TypeOfTestJPA Repository.
 *
 * @author mehaj
 */
public class TypeOfTestJPARepository extends BaseJpaRepository<TypeOfTest, Long> implements TypeOfTestRepository {

	/**
	 * Save type of test.
	 *
	 * @param typeOfTest the type of test
	 * @return TypeOfTest
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TypeOfTest saveTypeOfTest(TypeOfTest typeOfTest) {

		if (typeOfTest.getEntityId() == null || typeOfTest.getEntityId() == 0) {
			return super.save(typeOfTest);
		}
		entityManager.merge(typeOfTest);
		entityManager.flush();

		return this.load(typeOfTest.getEntityId());
	}

	/**
	 * delete type of test.
	 *
	 * @param typeOfTestId the type of test id
	 * @return the long
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public long deleteTypeOfTest(Long typeOfTestId) {

		super.delete(typeOfTestId);

		return typeOfTestId;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository#loadTypeOfTest(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TypeOfTest loadTypeOfTest(Long typeOfTestId) {

		return super.load(typeOfTestId);
	}
}
