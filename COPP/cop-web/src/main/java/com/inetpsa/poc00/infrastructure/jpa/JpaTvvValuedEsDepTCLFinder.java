package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLFinder;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLRepresentation;

/**
 * The Class JpaTvvValuedEsDepTCLFinder.
 */
public class JpaTvvValuedEsDepTCLFinder implements TvvValuedEsDepTCLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLFinder#getAllValuedTDL(long, long)
	 */
	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLFinder#getAllValuedTDL(long, long)
	 */
	@Override
	public List<TvvValuedEsDepTCLRepresentation> getAllValuedTDL(long tvvID, long emsID) {
		TypedQuery<TvvValuedEsDepTCLRepresentation> query = entityManager.createQuery("select new " + TvvValuedEsDepTCLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from TvvValuedEsDepTCL t where t.tvvObj.entityId =" + tvvID + " and t.emissionStandard.entityId = " + emsID, TvvValuedEsDepTCLRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLFinder#getAllValuedEsDepTCL(long, long)
	 */
	@Override
	public List<TvvValuedEsDepTCL> getAllValuedEsDepTCL(long tvvId, long emsId) {
		TypedQuery<TvvValuedEsDepTCL> query = entityManager.createQuery("select t from TvvValuedEsDepTCL t where t.tvvObj.entityId =" + tvvId + " and t.emissionStandard.entityId = " + emsId, TvvValuedEsDepTCL.class);
		return query.getResultList();
	}

}
