/*
 * Creation : Apr 1, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.generictd;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository;

/**
 * The Class GenericTestConditionJpaRepository.
 */
public class GenericTestConditionJpaRepository extends BaseJpaRepository<GenericTestCondition, Long> implements GenericTestConditionRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository#saveGenericTestCondition(com.inetpsa.poc00.domain.generictc.GenericTestCondition)
	 */
	@Override
	public GenericTestCondition saveGenericTestCondition(GenericTestCondition genericTestCondition) {
		if (genericTestCondition.getEntityId() != null && genericTestCondition.getEntityId() != 0)
			return super.save(genericTestCondition);
		super.persist(genericTestCondition);
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository#persistGenericTestCondition(com.inetpsa.poc00.domain.generictc.GenericTestCondition)
	 */
	@Override
	public void persistGenericTestCondition(GenericTestCondition object) {
		if (object.getEntityId() != null)
			super.save(object);
		else
			super.persist(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository#getAllGenericConditionsForEmsList(java.lang.Long)
	 */
	@Override
	public List<GenericTestCondition> getAllGenericConditionsForEmsList(Long entityId) {
		TypedQuery<GenericTestCondition> query = entityManager.createQuery("select c from GenericTestCondition c where c.emsDepTCL.entityId= " + entityId, GenericTestCondition.class);
		return query.getResultList();
	}

}
