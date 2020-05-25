/*
 * Creation : Jun 15, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedTestConditionFinder;

/**
 * The Class JpaValuedTestConditionFinder.
 */
public class JpaValuedTestConditionFinder implements TvvValuedTestConditionFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedTestConditionFinder#getAllValuedConditionForList(long)
	 */
	@Override
	public List<TvvValuedTestCondition> getAllValuedConditionForList(long listId) {
		TypedQuery<TvvValuedTestCondition> query = entityManager.createQuery("select d from TvvValuedTestCondition d where d.tvvValuedTCL.entityId= " + listId, TvvValuedTestCondition.class);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedTestConditionFinder#getAllValuedConditionForEmsDepList(long)
	 */
	@Override
	public List<TvvValuedTestCondition> getAllValuedConditionForEmsDepList(long listId) {
		TypedQuery<TvvValuedTestCondition> query = entityManager.createQuery("select d from TvvValuedTestCondition d where d.tvvValuedEsTCL.entityId= " + listId, TvvValuedTestCondition.class);
		return query.getResultList();
	}

}
