package com.inetpsa.poc00.infrastructure.jpa.tvvvalued;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCLRepository;

/**
 * The Class TvvValuedEsDepTCLJpaRepository.
 */
public class TvvValuedEsDepTCLJpaRepository extends BaseJpaRepository<TvvValuedEsDepTCL, Long> implements TvvValuedEsDepTCLRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCLRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCLRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCLRepository#getAllValuedEsDepTCL(long,
	 *      java.lang.Long)
	 */
	@Override
	public List<TvvValuedEsDepTCL> getAllValuedEsDepTCL(long tvvId, Long emsId) {
		TypedQuery<TvvValuedEsDepTCL> query = entityManager.createQuery("select t from TvvValuedEsDepTCL t where t.tvvObj.entityId =" + tvvId + " and t.emissionStandard.entityId = " + emsId, TvvValuedEsDepTCL.class);
		return query.getResultList();
	}

}
