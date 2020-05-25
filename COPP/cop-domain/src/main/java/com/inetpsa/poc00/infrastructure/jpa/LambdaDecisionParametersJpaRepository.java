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
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersRepository;

/**
 * The Class LambdaDecisionParametersJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "lambdaDecisionParameters")
public class LambdaDecisionParametersJpaRepository extends BaseJpaRepository<LambdaDecisionParameters, Long> implements LambdaDecisionParametersRepository {

	/** The jpa entity manager. */
	@Inject
	private EntityManager jpaEntityManager;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(LambdaDecisionParametersJpaRepository.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersRepository#saveLambdaDecisionParameters(com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters)
	 */
	@Override
	public LambdaDecisionParameters saveLambdaDecisionParameters(LambdaDecisionParameters lambdaDecisionParameters) {
		if (lambdaDecisionParameters.getEntityId() == null || lambdaDecisionParameters.getEntityId() == 0)
			return super.save(lambdaDecisionParameters);

		return jpaEntityManager.merge(lambdaDecisionParameters);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersRepository#persistLambdaDecisionParameters(com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters)
	 */
	@Override
	public LambdaDecisionParameters persistLambdaDecisionParameters(LambdaDecisionParameters lambdaDecisionParameters) {

		return save(lambdaDecisionParameters);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersRepository#deleteAll()
	 */
	@Override
	public int deleteAll() {
		try {

			return jpaEntityManager.createQuery("DELETE FROM LambdaDecisionParameters t").executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersRepository#getAllLambdaDecisionParameters()
	 */
	@Override
	public List<LambdaDecisionParameters> getAllLambdaDecisionParameters() {
		TypedQuery<LambdaDecisionParameters> query = jpaEntityManager.createQuery("SELECT l FROM LambdaDecisionParameters l ", LambdaDecisionParameters.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersRepository#deleteLambdaDecisionParameters(java.lang.Long)
	 */
	@Override
	public int deleteLambdaDecisionParameters(Long id) {
		try {

			return jpaEntityManager.createQuery("DELETE FROM LambdaDecisionParameters t where t.entityId = " + id).executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}

	}

}
