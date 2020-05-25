/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters;
import com.inetpsa.poc00.rest.lambdadecisionparameters.LambdaDecisionParametersFinder;

/**
 * The Class JpaLambdaDecisionParametersFinder.
 */
public class JpaLambdaDecisionParametersFinder implements LambdaDecisionParametersFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.lambdadecisionparameters.LambdaDecisionParametersFinder#getAllLambdaDecisionParameter()
	 */
	@Override
	public List<LambdaDecisionParameters> getAllLambdaDecisionParameter() {

		return entityManager.createQuery("SELECT ldp FROM LambdaDecisionParameters ldp").getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.lambdadecisionparameters.LambdaDecisionParametersFinder#getLambdaDecisionParameterByLabel(java.lang.String)
	 */
	@Override
	public List<LambdaDecisionParameters> getLambdaDecisionParameterByLabel(String label) {

		return entityManager.createQuery("SELECT ldp FROM LambdaDecisionParameters ldp WHERE ldp.fuelType.fuelTypeLable = ?1").setParameter(1, label).getResultList();
	}
}