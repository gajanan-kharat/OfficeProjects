/*
 * Creation : Jun 13, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.tvvvalued;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDLRepository;

/**
 * The Class TvvValuedEsDepTDLJpaRepository.
 */

public class TvvValuedEsDepTDLJpaRepository extends BaseJpaRepository<TvvValuedEsDepTDL, Long> implements TvvValuedEsDepTDLRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDLRepository#deleteAll()
	 */

	@Override
	public TvvValuedEsDepTDL saveTvvValuedEsDepTDL(TvvValuedEsDepTDL tvvValuedEsDepTDL) {

		return null;
	}

	@Override
	public void persistTvvValuedEsDepTDL(TvvValuedEsDepTDL tvvValuedEsDepTDL) {
		super.persist(tvvValuedEsDepTDL);
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDLRepository#deleteAll()
	 */
	@Override

	public long deleteAll() {

		return 0;
	}

	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDLRepository#getAllValuedEsDepTDL(long,
	 *      java.lang.Long)
	 */
	@Override
	public List<TvvValuedEsDepTDL> getAllValuedEsDepTDL(long tvvId, Long emsId) {
		TypedQuery<TvvValuedEsDepTDL> query = entityManager.createQuery("select t from TvvValuedEsDepTDL t where t.tvvObj.entityId =" + tvvId + " and t.emissionStandard.entityId = " + emsId, TvvValuedEsDepTDL.class);
		return query.getResultList();
	}

}
