/*
 * Creation : Nov 29, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.jpa.tvvvalued;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollGasLimitRepository;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;

/**
 * The Class TvvValuedPollGasLimitJpaRepository.
 */
public class TvvValuedPollGasLimitJpaRepository extends BaseJpaRepository<TvvValuedPollutantGasLimit, Long> implements TvvValuedPollGasLimitRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollGasLimitRepository#getAllLimitsForEmsDepList(java.lang.Long)
	 */
	@Override
	public List<TvvValuedPollutantGasLimit> getAllLimitsForEmsDepList(Long listId) {
		TypedQuery<TvvValuedPollutantGasLimit> query = entityManager.createQuery("select c from TvvValuedPollutantGasLimit c where c.tvvValuedEsDepPGL.entityId= " + listId, TvvValuedPollutantGasLimit.class);
		return query.getResultList();
	}

}
