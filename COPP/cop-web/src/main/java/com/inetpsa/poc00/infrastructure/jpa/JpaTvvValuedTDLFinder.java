/*
 * Creation : Jun 10, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTDLFinder;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTvvDepTDLRepresentation;

/**
 * The Class JpaTvvValuedTDLFinder.
 */
public class JpaTvvValuedTDLFinder implements TvvValuedTDLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTDLFinder#getAllTvvValuedTDL(long)
	 */
	@Override
	public List<TvvValuedTvvDepTDLRepresentation> getAllTvvValuedTDL(long tvvId) {
		TypedQuery<TvvValuedTvvDepTDLRepresentation> query = entityManager.createQuery("select new " + TvvValuedTvvDepTDLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from TvvValuedTvvDepTDL t where t.tvvObj.entityId =" + tvvId, TvvValuedTvvDepTDLRepresentation.class);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTDLFinder#getAllValuedTDL(long)
	 */
	@Override
	public List<TvvValuedTvvDepTDL> getAllValuedTDL(long tvvId) {
		TypedQuery<TvvValuedTvvDepTDL> query = entityManager.createQuery("select t from TvvValuedTvvDepTDL t where t.tvvObj.entityId =" + tvvId, TvvValuedTvvDepTDL.class);
		return query.getResultList();
	}

}
