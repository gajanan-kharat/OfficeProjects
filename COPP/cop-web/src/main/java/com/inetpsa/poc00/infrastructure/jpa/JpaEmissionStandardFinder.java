/*
 * Creation : Apr 14, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;

/**
 * The Class JpaEmissionStandardFinder.
 */
public class JpaEmissionStandardFinder implements EmissionStandardFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;
	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder#getAllEmissionStandards()
	 */
	@Override
	public List<EmissionStandardRepresentation> getAllEmissionStandards() {
		TypedQuery<EmissionStandardRepresentation> query = entityManager.createQuery("select new " + EmissionStandardRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.esLabel,t.status.entityId,t.status.label,t.status.guiLabel)" + " from EmissionStandard t order by t.esLabel asc,t.version*1 desc  ", EmissionStandardRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder#getEmissionStandardsWithLabel(java.lang.String)
	 */
	@Override
	public List<EmissionStandardRepresentation> getEmissionStandardsWithLabel(String label) {
		TypedQuery<EmissionStandardRepresentation> query = entityManager.createQuery("select new " + EmissionStandardRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.esLabel)" + " from EmissionStandard t where t.esLabel = '" + label + "'", EmissionStandardRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder#findAllReglementationData()
	 */
	@Override
	public List<EmissionStandardRepresentation> findAllReglementationData() {

		logger.info("querry running to get EmissionStandard value");
		TypedQuery<EmissionStandardRepresentation> query = entityManager.createQuery("select new " + EmissionStandardRepresentation.class.getName() + "(t1.lcdvCodeValue,t1.kmat,t1.genomeLcdvCodeValue.frLabel,t1.entityId,es.esLabel,es.description, es.entityId)" + " from GenomeTVVRule t1 left join t1.emissionStandard es"
				+ " where t1.lcdvCodeName='DKA' and t1.lcdvCodeValue = t1.genomeLcdvCodeValue.lcdvCodeValue GROUP BY t1.lcdvCodeValue,t1.kmat ORDER BY t1.entityId asc", EmissionStandardRepresentation.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc} Get MAX version available for given label -used to set new version
	 * 
	 * @see com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder#getMaxVersionForLabel(java.lang.String)
	 */
	@Override
	public Double getMaxVersionForLabel(String label) {

		Query query = entityManager.createNativeQuery(Constants.MAX_EMS_VERSION_QUERY);

		query.setParameter(1, label);
		return ((BigDecimal) query.getSingleResult()).doubleValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder#getAllEmissionStandardNames()
	 */
	@Override
	public List<String> getAllEmissionStandardNames() {
		TypedQuery<String> query = entityManager.createQuery("select  Distinct CONCAT(t.esLabel,t.version) as displayLabel" + " from EmissionStandard t", String.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder#emissionStandardData()
	 */
	@Override
	public List<EmissionStandardRepresentation> emissionStandardData() {

		logger.info("querry running to get EmissonStandard Data");

		TypedQuery<EmissionStandardRepresentation> query = entityManager.createQuery("select new " + EmissionStandardRepresentation.class.getName() + "(es.entityId,es.esLabel) from EmissionStandard es", EmissionStandardRepresentation.class);

		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder#getAllEmissionStandardsForTVV()
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder#getAllEmissionStandardsForTVV()
	 */
	@Override
	public List<EmissionStandardRepresentation> getAllEmissionStandardsForTVV() {
		TypedQuery<EmissionStandardRepresentation> query = entityManager.createQuery("select new " + EmissionStandardRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.esLabel,t.status.entityId,t.status.label,t.status.guiLabel)" + " from EmissionStandard t  where t.status.label like 'valid' order by t.entityId desc", EmissionStandardRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder#getEmissionStandardForRG()
	 */
	@Override
	public List<EmissionStandardRepresentation> getEmissionStandardForRG() {
		TypedQuery<EmissionStandardRepresentation> query = entityManager.createQuery("select new " + EmissionStandardRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.esLabel,t.status.entityId,t.status.label,t.status.guiLabel)" + " from EmissionStandard t  where t.status.label like 'valid' order by t.entityId desc", EmissionStandardRepresentation.class);
		return query.getResultList();
	}

}
