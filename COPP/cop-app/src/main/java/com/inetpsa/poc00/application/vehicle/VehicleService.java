/*
 * Creation : Oct 21, 2016
 */
package com.inetpsa.poc00.application.vehicle;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Interface VehicleService.
 */
@Service
public interface VehicleService {

	/**
	 * Creates the vehicle.
	 *
	 * @param vehicleObj the vehicle obj
	 * @param typeOfTestId the type of test id
	 * @return true, if successful
	 */
	public VehicleFile createVehicle(Vehicle vehicleObj, Long typeOfTestId);

	/**
	 * Save vehicle.
	 *
	 * @param vehicleObj the vehicle obj
	 * @return the vehicle
	 */
	Vehicle saveVehicle(Vehicle vehicleObj);

}
