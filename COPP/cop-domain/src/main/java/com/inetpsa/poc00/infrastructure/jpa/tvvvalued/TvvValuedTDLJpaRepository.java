/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.tvvvalued;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTDLRepository;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;

/**
 * The Class TvvValuedTDLJpaRepository.
 */
public class TvvValuedTDLJpaRepository extends BaseJpaRepository<TvvValuedTvvDepTDL, Long> implements TvvValuedTDLRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTDLRepository#saveTvvValuedTvvDepTDL(com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL)
	 */
	@Override
	public TvvValuedTvvDepTDL saveTvvValuedTvvDepTDL(TvvValuedTvvDepTDL tvvValuedTvvDepTDL) {
		if (tvvValuedTvvDepTDL.getEntityId() != null && tvvValuedTvvDepTDL.getEntityId() != 0)
			return super.save(tvvValuedTvvDepTDL);

		super.persist(tvvValuedTvvDepTDL);
		entityManager.flush();
		return super.load(tvvValuedTvvDepTDL.getEntityId());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTDLRepository#persistTvvValuedTvvDepTDL(com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL)
	 */
	@Override
	public void persistTvvValuedTvvDepTDL(TvvValuedTvvDepTDL tvvValuedTvvDepTDL) {
		super.persist(tvvValuedTvvDepTDL);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTDLRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTDLRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTDLRepository#deleteTvvValuedTvvDepTDL(long)
	 */
	@Override
	public void deleteTvvValuedTvvDepTDL(long entityId) {
		super.delete(entityId);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTDLRepository#getAllValuedTDL(long)
	 */
	@Override
	public List<TvvValuedTvvDepTDL> getAllValuedTDL(long tvvId) {
		TypedQuery<TvvValuedTvvDepTDL> query = entityManager.createQuery("select t from TvvValuedTvvDepTDL t where t.tvvObj.entityId =" + tvvId, TvvValuedTvvDepTDL.class);
		return query.getResultList();
	}

}
