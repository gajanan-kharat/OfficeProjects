/*
 * Creation : Jun 15, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.rest.tvvvaluedpgl.ValuedPollutantGasLimitFinder;

/**
 * The Class JpaValuedPollutantLimitFinder.
 */
public class JpaValuedPollutantLimitFinder implements ValuedPollutantGasLimitFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvvaluedpgl.ValuedPollutantGasLimitFinder#getAllLimitsForEmsDepList(long)
	 */
	@Override
	public List<TvvValuedPollutantGasLimit> getAllLimitsForEmsDepList(long listId) {
		TypedQuery<TvvValuedPollutantGasLimit> query = entityManager.createQuery("select c from TvvValuedPollutantGasLimit c where c.tvvValuedEsDepPGL.entityId= " + listId, TvvValuedPollutantGasLimit.class);
		return query.getResultList();
	}

}
