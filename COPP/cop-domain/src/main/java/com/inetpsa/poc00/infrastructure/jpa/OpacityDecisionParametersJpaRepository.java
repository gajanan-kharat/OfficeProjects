package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersRepository;

/**
 * The Class OpacityDecisionParametersJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "OpacityDecisionParameters")
public class OpacityDecisionParametersJpaRepository extends BaseJpaRepository<OpacityDecisionParameters, Long> implements OpacityDecisionParametersRepository {

	/** The jpa entity manager. */
	@Inject
	private EntityManager jpaEntityManager;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(OpacityDecisionParametersJpaRepository.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersRepository#saveOpacityDecisionParameters(com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters)
	 */
	@Override
	public OpacityDecisionParameters saveOpacityDecisionParameters(OpacityDecisionParameters opacityDecisionParameters) {
		if (opacityDecisionParameters.getEntityId() == null || opacityDecisionParameters.getEntityId() == 0)
			return super.save(opacityDecisionParameters);

		return jpaEntityManager.merge(opacityDecisionParameters);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersRepository#persistOpacityDecisionParameters(com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters)
	 */
	@Override
	public OpacityDecisionParameters persistOpacityDecisionParameters(OpacityDecisionParameters opacityDecisionParameters) {

		return save(opacityDecisionParameters);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersRepository#deleteAll()
	 */
	@Override
	public int deleteAll() {
		try {

			return jpaEntityManager.createQuery("DELETE FROM OpacityDecisionParameters odp").executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersRepository#getAllOpacityDecisionParameters()
	 */
	@Override
	public List<OpacityDecisionParameters> getAllOpacityDecisionParameters() {
		TypedQuery<OpacityDecisionParameters> query = jpaEntityManager.createQuery("SELECT odp FROM OpacityDecisionParameters odp ", OpacityDecisionParameters.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersRepository#deleteOpacityDecisionParameters(java.lang.Long)
	 */
	@Override
	public int deleteOpacityDecisionParameters(Long id) {
		try {

			return jpaEntityManager.createQuery("DELETE FROM OpacityDecisionParameters odp where odp.entityId = " + id).executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}

	}

}
