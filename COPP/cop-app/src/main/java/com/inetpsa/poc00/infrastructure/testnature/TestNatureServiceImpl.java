/*
 * Creation : Nov 4, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.testnature;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.testnature.TestNatureService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.testnature.TestNatureRepository;

/**
 * implementation for TestNatureService.
 *
 * @author mehaj
 */
public class TestNatureServiceImpl implements TestNatureService {
	/** The test nature repo. */
	@Inject
	private TestNatureRepository testNatureRepo;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.testnature.TestNatureService#saveTestNature(com.inetpsa.poc00.domain.testnature.TestNature)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TestNature saveTestNature(TestNature testnature) {
		return testNatureRepo.saveTestNature(testnature);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.testnature.TestNatureService#deleteTestNature(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void deleteTestNature(long testId) {
		testNatureRepo.deleteTestNature(testId);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.testnature.TestNatureService#updateTestNature(com.inetpsa.poc00.domain.testnature.TestNature)
	 */
	@Override
	@JpaUnit(Config.JPA_UNIT)
	public String updateTestNature(TestNature testnature) {

		List<TestNature> testNatureList = testNatureRepo.getAllTestNature();

		for (TestNature oldTn : testNatureList) {
			if (oldTn.getHierarchy() == testnature.getHierarchy() && oldTn.getEntityId() != testnature.getEntityId()) {
				return ConstantsApp.FAILURE;
			}
		}
		testNatureRepo.saveTestNature(testnature);

		return ConstantsApp.SUCCESS;

	}
}
