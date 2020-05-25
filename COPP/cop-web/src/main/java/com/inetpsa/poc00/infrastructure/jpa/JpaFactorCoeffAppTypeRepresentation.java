package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.rest.factorcoeffapptype.FactorCoeffAppTypeFinder;
import com.inetpsa.poc00.rest.factorcoeffapptype.FactorCoeffAppTypeRepresentation;

/**
 * The Class JpaFactorCoeffAppTypeRepresentation.
 */
public class JpaFactorCoeffAppTypeRepresentation implements FactorCoeffAppTypeFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.factorcoeffapptype.FactorCoeffAppTypeFinder#getAllFactorCoeffApplicationType()
	 */
	@Override
	public List<FactorCoeffAppTypeRepresentation> getAllFactorCoeffApplicationType() {

		TypedQuery<FactorCoeffAppTypeRepresentation> query = entityManager.createQuery("SELECT  new " + FactorCoeffAppTypeRepresentation.class.getName() + "(fcat.entityId, fcat.label) from FactorCoeffApplicationType fcat ", FactorCoeffAppTypeRepresentation.class);

		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.factorcoeffapptype.FactorCoeffAppTypeFinder#findFactorCoeffApplicationTypeDataByLabel(java.lang.String)
	 */
	@Override
	public List<FactorCoeffAppTypeRepresentation> findFactorCoeffApplicationTypeDataByLabel(String label) {

		TypedQuery<FactorCoeffAppTypeRepresentation> query = entityManager.createQuery("select new " + FactorCoeffAppTypeRepresentation.class.getName() + "(f.entityId) from FactorCoeffApplicationType f where f.label='" + label + "'", FactorCoeffAppTypeRepresentation.class);

		return query.getResultList();
	}

}
