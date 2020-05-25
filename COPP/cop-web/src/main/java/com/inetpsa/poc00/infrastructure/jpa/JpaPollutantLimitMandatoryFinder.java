/*
 * Creation : May 3, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.pollutantorgas.PollutantLimitMandatoryFinder;
import com.inetpsa.poc00.pollutantorgas.PollutantLimitMandatoryRepresentation;

/**
 * The Class JpaPollutantLimitMandatoryFinder.
 */
public class JpaPollutantLimitMandatoryFinder implements PollutantLimitMandatoryFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantLimitMandatoryFinder#getMandatoryDataForLimit(long)
	 */
	@Override
	public List<PollutantLimitMandatoryRepresentation> getMandatoryDataForLimit(long entityId) {
		TypedQuery<PollutantLimitMandatoryRepresentation> query = entityManager.createQuery("select new " + PollutantLimitMandatoryRepresentation.class.getName() + "(d.entityId, d.value,d.statusNature.entityId,d.statusNature.status.label,d.statusNature.testNature.type) from PollutantGasLmtMandatory d where d.pollutantGasLimit.entityId= " + entityId, PollutantLimitMandatoryRepresentation.class);
		return query.getResultList();
	}

}