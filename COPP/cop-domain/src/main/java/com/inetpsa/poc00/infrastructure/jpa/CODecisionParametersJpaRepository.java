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
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersRepository;
import com.inetpsa.poc00.domain.fuel.FuelRepository;

/**
 * The Class CODecisionParametersJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "CODecisionParameters")
public class CODecisionParametersJpaRepository extends BaseJpaRepository<CODecisionParameters, Long> implements CODecisionParametersRepository {

	/** The jpa entity manager. */
	@Inject
	private EntityManager jpaEntityManager;

	/** The fuel repository. */
	@Inject
	private FuelRepository fuelRepository;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(CODecisionParametersJpaRepository.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersRepository#saveCODecisionParameters(com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters)
	 */
	@Override
	public CODecisionParameters saveCODecisionParameters(CODecisionParameters cODecisionParameters) {
		if (cODecisionParameters.getEntityId() == null || cODecisionParameters.getEntityId() == 0) {
			logger.info("---------------------->cODecisionParameters" + cODecisionParameters);
			return super.save(cODecisionParameters);

		}
		return jpaEntityManager.merge(cODecisionParameters);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersRepository#persistCODecisionParameters(com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters)
	 */
	@Override
	public CODecisionParameters persistCODecisionParameters(CODecisionParameters cODecisionParameters) {

		return save(cODecisionParameters);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersRepository#deleteAll()
	 */
	@Override
	public int deleteAll() {
		try {

			return jpaEntityManager.createQuery("DELETE FROM CODecisionParameters odp").executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersRepository#getAllCODecisionParameters()
	 */
	@Override
	public List<CODecisionParameters> getAllCODecisionParameters() {
		TypedQuery<CODecisionParameters> query = jpaEntityManager.createQuery("SELECT cdp FROM CODecisionParameters cdp ", CODecisionParameters.class);
		List<CODecisionParameters> coDecisionParameterslist = query.getResultList();
		logger.info("------------------>coDecisionParameterslist" + coDecisionParameterslist);
		return coDecisionParameterslist;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersRepository#deleteCODecisionParameters(java.lang.Long)
	 */
	@Override
	public int deleteCODecisionParameters(Long id) {
		try {

			return jpaEntityManager.createQuery("DELETE FROM CODecisionParameters cdp where cdp.entityId = " + id).executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}

	}

}
