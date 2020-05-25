/*
 * Creation : Feb 29, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.generictd;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.generictd.GenericTechDataRepository;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;

/**
 * The Class GenericTechnicalDataJpaRepository.
 */
public class GenericTechnicalDataJpaRepository extends BaseJpaRepository<GenericTechnicalData, Long> implements GenericTechDataRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.generictd.GenericTechDataRepository#saveGenericTechData(com.inetpsa.poc00.domain.generictd.GenericTechnicalData)
	 */
	@Override
	public GenericTechnicalData saveGenericTechData(GenericTechnicalData genericTechData) {
		if (genericTechData.getEntityId() != null && genericTechData.getEntityId() != 0)
			return super.save(genericTechData);
		super.persist(genericTechData);
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.generictd.GenericTechDataRepository#persistGenericTechData(com.inetpsa.poc00.domain.generictd.GenericTechnicalData)
	 */
	@Override
	public void persistGenericTechData(GenericTechnicalData object) {

		if (object.getEntityId() != null)
			super.save(object);
		else
			super.persist(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.generictd.GenericTechDataRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.generictd.GenericTechDataRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.generictd.GenericTechDataRepository#getAllGenericDataForEmsList(java.lang.Long)
	 */
	@Override
	public List<GenericTechnicalData> getAllGenericDataForEmsList(Long entityId) {
		TypedQuery<GenericTechnicalData> query = entityManager.createQuery("select d from GenericTechnicalData d where d.emsDepTDL.entityId= " + entityId, GenericTechnicalData.class);
		return query.getResultList();
	}

}
