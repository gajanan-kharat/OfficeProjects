/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters;
import com.inetpsa.poc00.rest.codecisionparameters.CODecisionParametersFinder;

/**
 * The Class JpaCODecisionParametersFinder.
 */
public class JpaCODecisionParametersFinder implements CODecisionParametersFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.codecisionparameters.CODecisionParametersFinder#getAllCODecisionParameter()
	 */
	@Override
	public List<CODecisionParameters> getAllCODecisionParameter() {

		return entityManager.createQuery("SELECT codp FROM CODecisionParameters codp").getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.codecisionparameters.CODecisionParametersFinder#getCODecisionParameterByLabel(java.lang.String)
	 */
	@Override
	public List<CODecisionParameters> getCODecParamByFuelTypeLabel(String label) {

		return entityManager.createQuery("SELECT codp FROM CODecisionParameters codp WHERE codp.fuelType.fuelTypeLable = ?1").setParameter(1, label).getResultList();

	}
}