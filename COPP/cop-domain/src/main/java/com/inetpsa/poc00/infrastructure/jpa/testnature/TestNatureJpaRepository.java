package com.inetpsa.poc00.infrastructure.jpa.testnature;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.testnature.TestNatureRepository;

/**
 * The Class TestNatureJpaRepository.
 */
public class TestNatureJpaRepository extends BaseJpaRepository<TestNature, Long> implements TestNatureRepository {
	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(TestNatureJpaRepository.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#saveTestNature(com.inetpsa.poc00.domain.testnature.TestNature)
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#saveTestNature(com.inetpsa.poc00.domain.testnature.TestNature)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TestNature saveTestNature(TestNature testNature) {

		if (testNature.getEntityId() != null && testNature.getEntityId() != 0)
			return super.save(testNature);

		super.persist(testNature);
		entityManager.flush();

		return super.load(testNature.getEntityId());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#persistTestNature(com.inetpsa.poc00.domain.testnature.TestNature)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#persistTestNature(com.inetpsa.poc00.domain.testnature.TestNature)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistTestNature(TestNature object) {
		super.persist(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#deleteAll()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#count()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#deleteTestNature(long)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#deleteTestNature(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public long deleteTestNature(long testid) {

		try {
			return entityManager.createQuery("DELETE FROM TestNature where entityId=" + testid).executeUpdate();

		} catch (Exception e) {
			logger.error("Exception occurred while deleting Test Nature.", e);
			return 0;

		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#getAllTestNature()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#getAllTestNature()
	 */
	@Override
	public List<TestNature> getAllTestNature() {

		TypedQuery<TestNature> query = entityManager.createQuery("SELECT TN FROM TestNature TN", TestNature.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.testnature.TestNatureRepository#loadTestNature(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TestNature loadTestNature(Long testNatureId) {
		return super.load(testNatureId);
	}
}
