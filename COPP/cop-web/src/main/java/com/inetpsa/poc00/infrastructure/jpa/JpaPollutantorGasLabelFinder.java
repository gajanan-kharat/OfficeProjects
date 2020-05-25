/*
 * Creation : Apr 18, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelFinder;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

/**
 * The Class JpaPollutantorGasLabelFinder.
 */
public class JpaPollutantorGasLabelFinder implements PollutantGasLabelFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelFinder#getAllPGLables()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<PollutantGasLabelRepresentation> getAllPGLables() {
		TypedQuery<PollutantGasLabelRepresentation> query = entityManager.createQuery("select new " + PollutantGasLabelRepresentation.class.getName() + "(t.entityId,t.label)" + " from PollutantGasLabel t ", PollutantGasLabelRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelFinder#findPollutantGasLabelDataByLabel(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<PollutantGasLabelRepresentation> findPollutantGasLabelDataByLabel(String label) {

		TypedQuery<PollutantGasLabelRepresentation> query = entityManager.createQuery("select new " + PollutantGasLabelRepresentation.class.getName() + "(f.entityId) from PollutantGasLabel f where f.label='" + label + "'", PollutantGasLabelRepresentation.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelFinder#getAllPollutantData()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<PollutantGasLabelRepresentation> getAllPollutantData() {

		TypedQuery<PollutantGasLabelRepresentation> query = entityManager.createQuery("SELECT  new " + PollutantGasLabelRepresentation.class.getName() + "(polluGas.entityId, polluGas.label) from PollutantGasLabel polluGas ", PollutantGasLabelRepresentation.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelFinder#getPGLforStatisticalParameter(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<PollutantGasLabelRepresentation> getPGLforStatisticalParameter(Long statisticalRuleId) {
		TypedQuery<PollutantGasLabelRepresentation> query = entityManager.createQuery("select new " + PollutantGasLabelRepresentation.class.getName() + "(t.entityId,t.label)" + " from PollutantGasLabel t LEFT JOIN t.statisticalCalculationParameters scp where scp.statisticalCalculationRule.entityId=" + statisticalRuleId, PollutantGasLabelRepresentation.class);
		return query.getResultList(); // where scp.statisticalCalculationRule.entityId= + statisticalRuleId
	}

}
