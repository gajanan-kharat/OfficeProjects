/*
 * Creation : Nov 29, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.rest.statisticalsample.StatisticalSampleFinder;
import com.inetpsa.poc00.rest.statisticalsample.StatisticalSampleRepresentation;

/**
 * The Class JpaStatisticalSampleFinder.
 */
public class JpaStatisticalSampleFinder implements StatisticalSampleFinder {

	/** The entity manager. */
	@Inject
	EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.statisticalsample.StatisticalSampleFinder#getStatisticalSampleByTvv(java.lang.String)
	 */
	@Override
	public List<StatisticalSampleRepresentation> getStatisticalSampleByTvv(String tvvLabel) {

		TypedQuery<StatisticalSampleRepresentation> query = entityManager.createQuery(
				"SELECT new " + StatisticalSampleRepresentation.class.getName() + "(CONCAT(ss.technicalCase.emissionStandard.esLabel,' V', ss.technicalCase.emissionStandard.version), ss.carFactory.label, ss.technicalCase.technicalGroup.regulationGroup.statisticalCalculationRule.label) FROM StatisticalSample ss where ss.technicalCase.tvv.label='" + tvvLabel + "'",
				StatisticalSampleRepresentation.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.statisticalsample.StatisticalSampleFinder#getStatisticalSample(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<StatisticalSampleRepresentation> getStatisticalSample(String esLabel, String esVersion, String carFactoryLabel, String statisticalRuleLabel, String tvvLabel) {
		TypedQuery<StatisticalSampleRepresentation> query = entityManager.createQuery("SELECT new " + StatisticalSampleRepresentation.class.getName() + "(ss.entityId, min(pgr.creationDate), max(pgr.creationDate)) FROM StatisticalSample ss LEFT OUTER JOIN ss.pollutantGasResultSet pgr where ss.technicalCase.emissionStandard.esLabel='" + esLabel + "' and ss.technicalCase.emissionStandard.version = '"
				+ esVersion + "' and ss.carFactory.label = '" + carFactoryLabel + "' and ss.technicalCase.technicalGroup.regulationGroup.statisticalCalculationRule.label= '" + statisticalRuleLabel + "' and ss.technicalCase.tvv.label='" + tvvLabel + "' GROUP BY ss.entityId", StatisticalSampleRepresentation.class);

		return query.getResultList();
	}

}
