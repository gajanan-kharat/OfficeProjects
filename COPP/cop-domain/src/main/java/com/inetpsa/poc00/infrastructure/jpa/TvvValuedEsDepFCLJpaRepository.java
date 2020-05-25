/*
 * Creation : Jan 2, 2017
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedEsDepFCLRepository;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCL;

/**
 * The Class TvvValuedEsDepFCLJpaRepository.
 */
public class TvvValuedEsDepFCLJpaRepository extends BaseJpaRepository<TvvValuedEsDepFCL, Long> implements TvvValuedEsDepFCLRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedEsDepFCLRepository#getAllValuedEsDepFCL(long,
	 *      java.lang.Long)
	 */
	@Override
	public List<TvvValuedEsDepFCL> getAllValuedEsDepFCL(long tvvId, Long emsId) {
		TypedQuery<TvvValuedEsDepFCL> query = entityManager.createQuery("select t from TvvValuedEsDepFCL t where t.tvvObj.entityId =" + tvvId + " and t.emissionStandard.entityId = " + emsId, TvvValuedEsDepFCL.class);
		return query.getResultList();
	}

}
