package com.inetpsa.poc00.domain.vehicle;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface VehicleRepository.
 */
public interface VehicleRepository extends GenericRepository<Vehicle, Long> {

	/**
	 * Save vehicle.
	 *
	 * @param obj the obj
	 * @return the vehicle
	 */
	Vehicle saveVehicle(Vehicle obj);

	/**
	 * Check vehicle.
	 *
	 * @param condition the condition
	 * @return the list
	 */
	public List<Vehicle> checkVehicle(String condition);

	/**
	 * Update vehicle.
	 *
	 * @param vehicle the vehicle
	 * @return the vehicle
	 */
	public Vehicle updateVehicle(Vehicle vehicle);

	/**
	 * Load vehicle.
	 *
	 * @param vehicleId the vehicle id
	 * @return the vehicle
	 */
	Vehicle loadVehicle(Long vehicleId);

}
