package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLFinder;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLRepresentation;

/**
 * The Class JpaTvvValuedEsDepPGLFinder.
 */
public class JpaTvvValuedEsDepPGLFinder implements TvvValuedEsDepPGLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLFinder#getAllValuedPGL(long, long)
	 */
	@Override
	public List<TvvValuedEsDepPGLRepresentation> getAllValuedPGL(long tvvID, long emsID) {
		TypedQuery<TvvValuedEsDepPGLRepresentation> query = entityManager.createQuery("select new " + TvvValuedEsDepPGLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from TvvValuedEsDepPGL t where t.tvvObj.entityId =" + tvvID + " and t.emissionStandard.entityId = " + emsID, TvvValuedEsDepPGLRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLFinder#getAllValuedEsDepPGL(long, long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<TvvValuedEsDepPGL> getAllValuedEsDepPGL(long tvvId, long emsId) {
		TypedQuery<TvvValuedEsDepPGL> query = entityManager.createQuery("select t from TvvValuedEsDepPGL t where t.tvvObj.entityId =" + tvvId + " and t.emissionStandard.entityId = " + emsId, TvvValuedEsDepPGL.class);
		return query.getResultList();
	}

}
