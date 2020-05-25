package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TvvValuedTCLFinder;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TvvValuedTvvDepTCLRepresentation;

/**
 * The Class JpaTvvValuedTCLFinder.
 */
public class JpaTvvValuedTCLFinder implements TvvValuedTCLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvvaluedtcl.TvvValuedTCLFinder#getAllTvvValuedTCL(long)
	 */
	@Override
	public List<TvvValuedTvvDepTCLRepresentation> getAllTvvValuedTCL(long tvvId) {
		TypedQuery<TvvValuedTvvDepTCLRepresentation> query = entityManager.createQuery("select new " + TvvValuedTvvDepTCLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from TvvValuedTvvDepTCL t where t.tvvObj.entityId =" + tvvId, TvvValuedTvvDepTCLRepresentation.class);
		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvvaluedtcl.TvvValuedTCLFinder#getAllValuedTCL(long)
	 */
	@Override
	public List<TvvValuedTvvDepTCL> getAllValuedTCL(long tvvId) {
		TypedQuery<TvvValuedTvvDepTCL> query = entityManager.createQuery("select t from TvvValuedTvvDepTCL t where t.tvvObj.entityId =" + tvvId, TvvValuedTvvDepTCL.class);
		return query.getResultList();
	}

}
