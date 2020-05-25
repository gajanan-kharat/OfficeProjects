package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;

/**
 * The Class VehicleJpaRepository.
 */

public class VehicleJpaRepository extends BaseJpaRepository<Vehicle, Long> implements VehicleRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.vehicle.VehicleRepository#saveVehicle(com.inetpsa.poc00.domain.vehicle.Vehicle)
	 */

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Vehicle saveVehicle(Vehicle obj) {
		return super.save(obj);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.vehicle.VehicleRepository#checkVehicle(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<Vehicle> checkVehicle(String condition) {
		TypedQuery<Vehicle> query = entityManager.createQuery("FROM Vehicle where " + condition, Vehicle.class);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.vehicle.VehicleRepository#updateVehicle(com.inetpsa.poc00.domain.vehicle.Vehicle)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Vehicle updateVehicle(Vehicle vehicle) {
		return entityManager.merge(vehicle);
	}

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Vehicle loadVehicle(Long vehicleId) {
		return super.load(vehicleId);
	}
}
