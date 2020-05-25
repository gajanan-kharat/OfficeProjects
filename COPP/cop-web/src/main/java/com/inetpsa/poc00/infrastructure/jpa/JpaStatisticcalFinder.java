/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.rest.statisticalcalculationrule.StatisticalCalculationRuleRepresentation;
import com.inetpsa.poc00.rest.statisticalcalculationrule.StatisticalcalFinder;

/**
 * The Class JpaStatisticcalFinder.
 */
public class JpaStatisticcalFinder implements StatisticalcalFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.statisticalcalculationrule.StatisticalcalFinder#getAllstatisticalRule()
	 */
	@Override
	public List<StatisticalCalculationRuleRepresentation> getAllstatisticalRule() {

		TypedQuery<StatisticalCalculationRuleRepresentation> query = entityManager.createQuery("select new " + StatisticalCalculationRuleRepresentation.class.getName() + "(statisticalRule.entityId,statisticalRule.label,statisticalRule.description)" + " from StatisticalCalculationRule statisticalRule ", StatisticalCalculationRuleRepresentation.class);
		return query.getResultList();
	}

}
