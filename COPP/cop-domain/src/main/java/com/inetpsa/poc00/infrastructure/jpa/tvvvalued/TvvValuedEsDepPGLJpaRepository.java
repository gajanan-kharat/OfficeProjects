package com.inetpsa.poc00.infrastructure.jpa.tvvvalued;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGLRepository;

public class TvvValuedEsDepPGLJpaRepository extends BaseJpaRepository<TvvValuedEsDepPGL, Long> implements TvvValuedEsDepPGLRepository {
	/**
	 * Get TvvVAluedEsDepPGL based on TvvId and EmsId {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGLRepository#getAllValuedEsDepPGL(long, java.lang.Long)
	 */
	@Override
	public List<TvvValuedEsDepPGL> getAllValuedEsDepPGL(long tvvId, Long emsId) {
		TypedQuery<TvvValuedEsDepPGL> query = entityManager.createQuery("select t from TvvValuedEsDepPGL t where t.tvvObj.entityId =" + tvvId + " and t.emissionStandard.entityId = " + emsId, TvvValuedEsDepPGL.class);
		return query.getResultList();
	}

}
