/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyFinder;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;

/**
 * The Class JpaVehicleTechnologyFinder.
 */
public class JpaVehicleTechnologyFinder implements VehicleTechnologyFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyFinder#getAllVehicleTechnologies()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleTechnologyRepresentation> getAllVehicleTechnologies() {
		TypedQuery<VehicleTechnologyRepresentation> query = entityManager.createQuery("select new " + VehicleTechnologyRepresentation.class.getName() + "(t.entityId, t.description,t.label)" + " from VehicleTechnology t ", VehicleTechnologyRepresentation.class);
		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyFinder#findVehicleTechnologyDataByLabel(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleTechnologyRepresentation> findVehicleTechnologyDataByLabel(String label) {

		TypedQuery<VehicleTechnologyRepresentation> query = entityManager.createQuery("select new " + VehicleTechnologyRepresentation.class.getName() + "(f.entityId) from VehicleTechnology f where f.label='" + label + "'", VehicleTechnologyRepresentation.class);

		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyFinder#getAllVTechnologiesForES(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleTechnologyRepresentation> getAllVTechnologiesForES(long emsId) {
		TypedQuery<VehicleTechnologyRepresentation> query = entityManager.createQuery("select new " + VehicleTechnologyRepresentation.class.getName() + "(t.entityId, t.description,t.label)" + " from VehicleTechnology t INNER JOIN t.emissionStandards e where e.entityId = " + emsId, VehicleTechnologyRepresentation.class);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyFinder#getAllVTechnologiesForFCList(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleTechnologyRepresentation> getAllVTechnologiesForFCList(long fcListId) {
		TypedQuery<VehicleTechnologyRepresentation> query = entityManager.createQuery("select new " + VehicleTechnologyRepresentation.class.getName() + "(t.entityId, t.description,t.label)" + " from VehicleTechnology t INNER JOIN t.factorCoefficentList f where f.entityId = " + fcListId, VehicleTechnologyRepresentation.class);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyFinder#getAllTechnologiesForCopiedES(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleTechnology> getAllTechnologiesForCopiedES(Long emsId) {
		TypedQuery<VehicleTechnology> query = entityManager.createQuery("select t from VehicleTechnology t INNER JOIN t.emissionStandards e where e.entityId = " + emsId, VehicleTechnology.class);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyFinder#getAllVTForCopiedPGList(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleTechnology> getAllVTForCopiedPGList(Long entityId) {
		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTAVT C JOIN COPQTMPV L ON (L.VTECHNOLOGY_ID = C.ID) JOIN COPQTPLL PGL ON (PGL.ID = L.PGLIST_ID) WHERE PGL.ID =?1", VehicleTechnology.class);

		query.setParameter(1, entityId);

		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyFinder#getAllVTForCopiedFCList(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleTechnology> getAllVTForCopiedFCList(Long entityId) {

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTAVT C JOIN COPQTMFV L ON (L.VTECHNOLOGYID = C.ID) JOIN COPQTFLS FCL ON (FCL.ID =  L.FCLISTID) WHERE FCL.ID = ?1", VehicleTechnology.class);

		query.setParameter(1, entityId);

		return query.getResultList();

	}

}
