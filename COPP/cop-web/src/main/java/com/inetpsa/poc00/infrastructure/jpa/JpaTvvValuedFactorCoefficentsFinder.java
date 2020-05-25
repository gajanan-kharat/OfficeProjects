/*
 * Creation : Jun 16, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;
import com.inetpsa.poc00.rest.tvvvaluedfcl.ValuedFactorCoefficentFinder;

/**
 * The Class JpaTvvValuedFactorCoefficentsFinder.
 */
public class JpaTvvValuedFactorCoefficentsFinder implements ValuedFactorCoefficentFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedfcl.ValuedFactorCoefficentFinder#getAllFactorsForEmsDepList(long)
	 */
	@Override
	public List<TvvValuedFactorCoefficents> getAllFactorsForEmsDepList(long listId) {
		TypedQuery<TvvValuedFactorCoefficents> query = entityManager.createQuery("select f from TvvValuedFactorCoefficents f where f.fcList.entityId= " + listId, TvvValuedFactorCoefficents.class);
		return query.getResultList();
	}

}
