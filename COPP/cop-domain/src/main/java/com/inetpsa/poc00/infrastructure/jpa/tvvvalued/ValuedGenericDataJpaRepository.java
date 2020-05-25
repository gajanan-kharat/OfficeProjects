/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.tvvvalued;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataRepository;

/**
 * The Class ValuedGenericDataJpaRepository.
 */
public class ValuedGenericDataJpaRepository extends BaseJpaRepository<TvvValuedTechData, Long> implements TvvValuedTechDataRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataRepository#saveTvvValuedTechData(com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData)
	 */
	@Override
	public TvvValuedTechData saveTvvValuedTechData(TvvValuedTechData genericTechData) {
		if (genericTechData.getEntityId() != null && genericTechData.getEntityId() != 0)
			return super.save(genericTechData);
		super.persist(genericTechData);
		entityManager.flush();
		return super.load(genericTechData.getEntityId());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataRepository#persistTvvValuedTechData(com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData)
	 */
	@Override
	public void persistTvvValuedTechData(TvvValuedTechData object) {
		if (object.getEntityId() != null)
			super.save(object);
		else
			super.persist(object);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataRepository#getAllValuedDataForList(java.lang.Long)
	 */
	@Override
	public List<TvvValuedTechData> getAllValuedDataForList(Long listId) {
		TypedQuery<TvvValuedTechData> query = entityManager.createQuery("select d from TvvValuedTechData d where d.tvvValuedTDL.entityId= " + listId, TvvValuedTechData.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataRepository#getAllValuedDataForEmsDepList(java.lang.Long)
	 */
	@Override
	public List<TvvValuedTechData> getAllValuedDataForEmsDepList(Long listId) {
		TypedQuery<TvvValuedTechData> query = entityManager.createQuery("select d from TvvValuedTechData d where d.tvvValuedEsTDL.entityId= " + listId, TvvValuedTechData.class);
		return query.getResultList();
	}

}
