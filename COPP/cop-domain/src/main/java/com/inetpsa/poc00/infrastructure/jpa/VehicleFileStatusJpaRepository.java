/*
 * Creation : Oct 24, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusRepository;

/**
 * The Class VehicleFileStatusJpaRepository.
 */
public class VehicleFileStatusJpaRepository extends BaseJpaRepository<VehicleFileStatus, Long> implements VehicleFileStatusRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusRepository#getVehicleFileStatusbyLabel(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public VehicleFileStatus getVehicleFileStatusbyLabel(String label) {
		TypedQuery<VehicleFileStatus> query = entityManager.createQuery("from VehicleFileStatus where LABEL= '" + label + "'", VehicleFileStatus.class);

		List<VehicleFileStatus> vehicleFileStatusList = query.getResultList();

		if (vehicleFileStatusList.isEmpty())
			return null;

		return vehicleFileStatusList.get(0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusRepository#loadVehicleFileStatus(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public VehicleFileStatus loadVehicleFileStatus(Long id) {
		return super.load(id);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusRepository#saveVehicleFileStatus(com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public VehicleFileStatus saveVehicleFileStatus(VehicleFileStatus vehicleFileStatusObj) {
		return super.save(vehicleFileStatusObj);
	}

}
