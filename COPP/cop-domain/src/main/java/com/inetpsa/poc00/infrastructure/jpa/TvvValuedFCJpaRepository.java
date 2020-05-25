/*
 * Creation : Jan 2, 2017
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedFactorCoefficentRepository;

public class TvvValuedFCJpaRepository extends BaseJpaRepository<TvvValuedFactorCoefficents, Long> implements TvvValuedFactorCoefficentRepository {

	@Override
	public List<TvvValuedFactorCoefficents> getAllFactorsForEmsDepList(Long listId) {
		TypedQuery<TvvValuedFactorCoefficents> query = entityManager.createQuery("select f from TvvValuedFactorCoefficents f where f.fcList.entityId= " + listId, TvvValuedFactorCoefficents.class);
		return query.getResultList();
	}

}
