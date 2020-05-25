/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.tvvvalued;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionRepository;

/**
 * The Class ValuedGenericConditionJpaRepository.
 */
public class ValuedGenericConditionJpaRepository extends BaseJpaRepository<TvvValuedTestCondition, Long> implements TvvValuedTestConditionRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionRepository#saveTvvValuedTestCondtn(com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition)
	 */
	@Override
	public TvvValuedTestCondition saveTvvValuedTestCondtn(TvvValuedTestCondition genericTestCondition) {
		if (genericTestCondition.getEntityId() != null && genericTestCondition.getEntityId() != 0)
			return super.save(genericTestCondition);
		super.persist(genericTestCondition);
		entityManager.flush();
		return super.load(genericTestCondition.getEntityId());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionRepository#persistTvvValuedTestCondition(com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition)
	 */
	@Override
	public void persistTvvValuedTestCondition(TvvValuedTestCondition genericTestCondition) {
		if (genericTestCondition.getEntityId() != null)
			super.save(genericTestCondition);
		else
			super.persist(genericTestCondition);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionRepository#getAllValuedConditionForEmsDepList(java.lang.Long)
	 */
	@Override
	public List<TvvValuedTestCondition> getAllValuedConditionForEmsDepList(Long listId) {
		TypedQuery<TvvValuedTestCondition> query = entityManager.createQuery("select d from TvvValuedTestCondition d where d.tvvValuedEsTCL.entityId= " + listId, TvvValuedTestCondition.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionRepository#getAllValuedConditionForList(java.lang.Long)
	 */
	@Override
	public List<TvvValuedTestCondition> getAllValuedConditionForList(Long listId) {
		TypedQuery<TvvValuedTestCondition> query = entityManager.createQuery("select d from TvvValuedTestCondition d where d.tvvValuedTCL.entityId= " + listId, TvvValuedTestCondition.class);
		return query.getResultList();
	}

}