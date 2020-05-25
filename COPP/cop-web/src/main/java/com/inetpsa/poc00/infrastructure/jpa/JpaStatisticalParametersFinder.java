/*
 * Creation : Dec 2, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.rest.statisticalrulesparameters.StatisticalParameterRepresentation;
import com.inetpsa.poc00.rest.statisticalrulesparameters.StatisticalParametersFinder;

/**
 * The Class JpaStatisticalParametersFinder.
 */
public class JpaStatisticalParametersFinder implements StatisticalParametersFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.statisticalrulesparameters.StatisticalParametersFinder#getStatisticalParameterList(java.lang.Long)
	 */
	@Override
	public List<StatisticalParameterRepresentation> getStatisticalParameterList(Long statisticalRuleId) {
		TypedQuery<StatisticalParameterRepresentation> query = entityManager.createQuery("SELECT new " + StatisticalParameterRepresentation.class.getName() + "(scp.entityId,scp.uncertainityFactor,scp.limit1,scp.limit2,scp.pollutantGasLabel.entityId) FROM StatisticalCalculationParameters scp where scp.statisticalCalculationRule.entityId =" + statisticalRuleId,
				StatisticalParameterRepresentation.class);
		return query.getResultList();
	}

}
