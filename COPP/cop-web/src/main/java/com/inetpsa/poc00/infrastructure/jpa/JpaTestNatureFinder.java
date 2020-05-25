package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.rest.testnature.TestNatureFinder;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentation;

/**
 * The Class JpaTestNatureFinder.
 */
public class JpaTestNatureFinder implements TestNatureFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.testnature.TestNatureFinder#getAllTestNature()
	 */
	/**/
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<TestNatureRepresentation> getAllTestNature() {

		TypedQuery<TestNatureRepresentation> query = entityManager.createQuery("select new " + TestNatureRepresentation.class.getName() + "(test.entityId, test.label,test.type,test.hierarchy)" + " from TestNature test ", TestNatureRepresentation.class);

		return query.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.testnature.TestNatureFinder#findTestNature(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TestNature findTestNature(String testNatureType) {

		String queryString = "from TestNature testNature where testNature.type = ?1";

		Query query = entityManager.createQuery(queryString);

		query.setParameter(1, testNatureType);

		List<TestNature> testNatures = query.getResultList();
		if (!testNatures.isEmpty()) {
			return testNatures.get(0);
		} else {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.testnature.TestNatureFinder#isTestNatureUsed(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Boolean isTestNatureUsedInStatus(Long testNatureId) {
		String queryString = "SELECT TEST_ID FROM COPQTMSN WHERE TEST_ID = ?1";
		Query query = entityManager.createNativeQuery(queryString);
		query.setParameter(1, testNatureId);
		if (query.getResultList().isEmpty())
			return Boolean.FALSE;
		return Boolean.TRUE;
	}

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Boolean isTestNatureUsedInTypeTest(Long testNatureId) {

		String queryString = "SELECT TEST_NATURE_ID FROM COPQTTOT WHERE TEST_NATURE_ID = ?1";

		Query query = entityManager.createNativeQuery(queryString);

		query.setParameter(1, testNatureId);

		if (query.getResultList().isEmpty())
			return Boolean.FALSE;

		return Boolean.TRUE;
	}

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<Long> testnatureList(Long typeOfTestId) {
		TypedQuery<Long> query = entityManager.createQuery("SELECT tn.entityId FROM TestNature tn where tn.hierarchy <=" + "(SELECT tot.testNature.hierarchy FROM TypeOfTest tot where tot.entityId= " + typeOfTestId + ")", Long.class);

		return query.getResultList();
	}
}