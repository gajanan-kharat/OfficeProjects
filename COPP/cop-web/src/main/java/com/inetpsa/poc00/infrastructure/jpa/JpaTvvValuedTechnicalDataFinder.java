/*
 * Creation : Jun 14, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;
import com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataFinder;

/**
 * The Class JpaTvvValuedTechnicalDataFinder.
 */
public class JpaTvvValuedTechnicalDataFinder implements ValuedGenericDataFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataFinder#getAllValuedDataForList(long)
	 */
	@Override
	public List<TvvValuedTechData> getAllValuedDataForList(long listId) {
		TypedQuery<TvvValuedTechData> query = entityManager.createQuery("select d from TvvValuedTechData d where d.tvvValuedTDL.entityId= " + listId, TvvValuedTechData.class);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataFinder#getAllValuedDataForEmsDepList(long)
	 */
	@Override
	public List<TvvValuedTechData> getAllValuedDataForEmsDepList(long listId) {
		TypedQuery<TvvValuedTechData> query = entityManager.createQuery("select d from TvvValuedTechData d where d.tvvValuedEsTDL.entityId= " + listId, TvvValuedTechData.class);
		return query.getResultList();
	}

}
