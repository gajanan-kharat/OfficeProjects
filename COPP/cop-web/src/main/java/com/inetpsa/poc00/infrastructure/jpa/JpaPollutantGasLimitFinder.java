/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.mandatorydata.MandatoryDataRepresentation;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitRepresentation;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitsFinder;

/**
 * The Class JpaPollutantGasLimitFinder.
 */
public class JpaPollutantGasLimitFinder implements PollutantGasLimitsFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantGasLimitsFinder#getAllDataForEMSDepList(long)
	 */
	@Override
	public List<PollutantGasLimitRepresentation> getAllDataForEMSDepList(long entityId) {
		TypedQuery<PollutantGasLimitRepresentation> query = entityManager.createQuery("select new " + PollutantGasLimitRepresentation.class.getName() + "(f.entityId, f.maxDValue,f.maxDValRule,f.minDValue,f.minDValRule,f.pgLabel.entityId,f.pgLabel.label)" + " from PollutantGasLimit f where f.pollutantGasLimitList.entityId= " + entityId, PollutantGasLimitRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantGasLimitsFinder#getAllPollutantsForDepList(long)
	 */
	@Override
	public List<PollutantGasLimit> getAllPollutantsForDepList(long entityId) {
		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTPLT C WHERE C.PGLIST_ID = ?1", PollutantGasLimit.class);
		query.setParameter(1, entityId);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantGasLimitsFinder#getAllMinPGLimitsForLabel(java.lang.String)
	 */
	@Override
	public List<Double> getAllMinPGLimitsForLabel(String label) {
		TypedQuery<Double> query = entityManager.createQuery("select distinct f.minDValue from PollutantGasLimit f where f.pgLabel.label= '" + label + "'", Double.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantGasLimitsFinder#getAllMaxPGLimitsForLabel(java.lang.String)
	 */
	@Override
	public List<Double> getAllMaxPGLimitsForLabel(String label) {
		TypedQuery<Double> query = entityManager.createQuery("select distinct f.maxDValue from PollutantGasLimit f where f.pgLabel.label= '" + label + "'", Double.class);
		return query.getResultList();
	}

	@Override
	public List<MandatoryDataRepresentation> getAllPollutantGasLimit(String emsVersion, long emsId) {

		TypedQuery<MandatoryDataRepresentation> query = entityManager.createQuery("select new " + MandatoryDataRepresentation.class.getName() + "(d.entityId,d.pgLabel.label ,d.pollutantGasLimitList.label, d.pollutantGasLimitList.emissionStandard.esLabel, 'Limite',d.mandatoryIdValues) from PollutantGasLimit d Where d.pollutantGasLimitList.emissionStandard.version = " + emsVersion
				+ "AND d.pollutantGasLimitList.emissionStandard.entityId = " + emsId + " AND pollutantGasLimitList.entityId IS NOT NULL", MandatoryDataRepresentation.class);
		return query.getResultList();

	}

}
