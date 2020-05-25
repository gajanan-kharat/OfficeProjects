/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.tvvvalued;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTCLRepository;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;

/**
 * The Class TvvValuedTCLJpaRepository.
 */
public class TvvValuedTCLJpaRepository extends BaseJpaRepository<TvvValuedTvvDepTCL, Long> implements TvvValuedTCLRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTCLRepository#saveTvvValuedTvvDepTCL(com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL)
	 */
	@Override
	public TvvValuedTvvDepTCL saveTvvValuedTvvDepTCL(TvvValuedTvvDepTCL tvvValuedTvvDepTCL) {
		if (tvvValuedTvvDepTCL.getEntityId() != null && tvvValuedTvvDepTCL.getEntityId() != 0)
			return super.save(tvvValuedTvvDepTCL);

		super.persist(tvvValuedTvvDepTCL);
		entityManager.flush();
		return super.load(tvvValuedTvvDepTCL.getEntityId());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTCLRepository#persistTvvValuedTvvDepTCL(com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL)
	 */
	@Override
	public void persistTvvValuedTvvDepTCL(TvvValuedTvvDepTCL tvvValuedTvvDepTCL) {
		super.persist(tvvValuedTvvDepTCL);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTCLRepository#deleteTvvValuedTvvDepTCL(long)
	 */
	@Override
	public void deleteTvvValuedTvvDepTCL(long id) {
		super.delete(id);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTCLRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTCLRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTCLRepository#getAllValuedTCL(long)
	 */
	@Override
	public List<TvvValuedTvvDepTCL> getAllValuedTCL(long tvvId) {
		TypedQuery<TvvValuedTvvDepTCL> query = entityManager.createQuery("select t from TvvValuedTvvDepTCL t where t.tvvObj.entityId =" + tvvId, TvvValuedTvvDepTCL.class);
		return query.getResultList();
	}

}
