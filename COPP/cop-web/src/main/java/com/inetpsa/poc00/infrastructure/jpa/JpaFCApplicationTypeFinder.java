/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.emsdepfcl.FactorCoeffApplicationTypeFinder;
import com.inetpsa.poc00.emsdepfcl.FactorCoeffApplicationTypeRepresentation;

public class JpaFCApplicationTypeFinder implements FactorCoeffApplicationTypeFinder {

	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdepfcl.FactorCoeffApplicationTypeFinder#getAllApplicationTypes()
	 */
	@Override
	public List<FactorCoeffApplicationTypeRepresentation> getAllApplicationTypes() {
		TypedQuery<FactorCoeffApplicationTypeRepresentation> query = entityManager.createQuery("select new " + FactorCoeffApplicationTypeRepresentation.class.getName() + "(t.entityId,t.label)" + " from FactorCoeffApplicationType t ", FactorCoeffApplicationTypeRepresentation.class);
		return query.getResultList();
	}

}
