/*
 * Creation : Apr 19, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.factororcoeffiecient;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffRepository;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;

/**
 * The Class FactorOrCoefficientJparEpository.
 */
public class FactorOrCoefficientJparEpository extends BaseJpaRepository<FactorCoefficents, Long> implements FactorCoeffRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffRepository#deletefactorCoefficient(long)
	 */
	@Override
	public void deletefactorCoefficient(long entityId) {
		super.delete(entityId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffRepository#saveFactorCoefficents(com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents)
	 */
	@Override
	public FactorCoefficents saveFactorCoefficents(FactorCoefficents fcObj) {
		if (fcObj.getEntityId() != null && fcObj.getEntityId() != 0)
			return super.save(fcObj);
		super.persist(fcObj);
		entityManager.flush();

		return this.load(fcObj.getEntityId());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffRepository#persistFactorCoefficents(com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents)
	 */
	@Override
	public void persistFactorCoefficents(FactorCoefficents object) {
		super.persist(object);

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffRepository#getAllFActorsForDepList(java.lang.Long)
	 */
	@Override
	public List<FactorCoefficents> getAllFActorsForDepList(Long entityId) {
		TypedQuery<FactorCoefficents> query = entityManager.createQuery("select f from FactorCoefficents f where f.fcList.entityId= " + entityId, FactorCoefficents.class);
		return query.getResultList();
	}

}
