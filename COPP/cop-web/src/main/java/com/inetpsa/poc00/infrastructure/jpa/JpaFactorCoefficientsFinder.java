/*
 * Creation : Apr 18, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentsFinder;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentsRepresentation;

/**
 * The Class JpaFactorCoefficientsFinder.
 */
public class JpaFactorCoefficientsFinder implements FactorCoefficentsFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdepfcl.FactorCoefficentsFinder#getAllDataForEMSDepList(long)
	 */
	@Override
	public List<FactorCoefficentsRepresentation> getAllDataForEMSDepList(long entityId) {
		TypedQuery<FactorCoefficentsRepresentation> query = entityManager.createQuery("select new " + FactorCoefficentsRepresentation.class.getName() + "(f.entityId, f.defaultValue,f.fcLabel.entityId,f.fcLabel.label,f.pgLabel.entityId,f.pgLabel.label)" + " from FactorCoefficents f where f.fcList.entityId= " + entityId, FactorCoefficentsRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdepfcl.FactorCoefficentsFinder#getAllFActorsForDepList(long)
	 */
	@Override
	public List<FactorCoefficents> getAllFActorsForDepList(long entityId) {
		TypedQuery<FactorCoefficents> query = entityManager.createQuery("select f from FactorCoefficents f where f.fcList.entityId= " + entityId, FactorCoefficents.class);
		return query.getResultList();
	}

}
