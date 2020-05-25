/*
 * Creation : Jun 13, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;
import com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLFinder;
import com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLRepresentation;

/**
 * The Class JpaTvvValuedEsDepTDLAssembler.
 */
public class JpaTvvValuedEsDepTDLFinder implements TvvValuedEsDepTDLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLFinder#getAllValuedTDL(long, long)
	 */
	@Override
	public List<TvvValuedEsDepTDLRepresentation> getAllValuedTDL(long tvvID, long emsId) {
		TypedQuery<TvvValuedEsDepTDLRepresentation> query = entityManager.createQuery("select new " + TvvValuedEsDepTDLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from TvvValuedEsDepTDL t where t.tvvObj.entityId =" + tvvID + " and t.emissionStandard.entityId = " + emsId, TvvValuedEsDepTDLRepresentation.class);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLFinder#getAllValuedEsDepTDL(long, long)
	 */
	@Override
	public List<TvvValuedEsDepTDL> getAllValuedEsDepTDL(long tvvId, long emsId) {
		TypedQuery<TvvValuedEsDepTDL> query = entityManager.createQuery("select t from TvvValuedEsDepTDL t where t.tvvObj.entityId =" + tvvId + " and t.emissionStandard.entityId = " + emsId, TvvValuedEsDepTDL.class);
		return query.getResultList();
	}

}
