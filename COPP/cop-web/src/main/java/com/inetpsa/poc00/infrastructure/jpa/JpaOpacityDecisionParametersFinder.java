/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters;
import com.inetpsa.poc00.rest.opacitydecisionparameters.OpacityDecisionParametersFinder;

/**
 * The Class JpaOpacityDecisionParametersFinder.
 */
public class JpaOpacityDecisionParametersFinder implements OpacityDecisionParametersFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.opacitydecisionparameters.OpacityDecisionParametersFinder#getAllOpacityDecisionParameter()
	 */
	@Override
	public List<OpacityDecisionParameters> getAllOpacityDecisionParameter() {

		return entityManager.createQuery("SELECT odp FROM OpacityDecisionParameters odp").getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.opacitydecisionparameters.OpacityDecisionParametersFinder#getOpacityDecisionParameterByLabel(java.lang.String)
	 */
	@Override
	public List<OpacityDecisionParameters> getOpacityDecisionParameterByLabel(String label) {

		return entityManager.createQuery("SELECT odp FROM OpacityDecisionParameters odp WHERE odp.fuelType.fuelTypeLable = ?1").setParameter(1, label).getResultList();
	}
}