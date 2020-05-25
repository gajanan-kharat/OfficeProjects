package com.inetpsa.poc00.domain.vehicle;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating Vehicle objects.
 */
public interface VehicleFactory extends GenericFactory<Vehicle> {

	/**
	 * Creates a new Vehicle object.
	 * 
	 * @return the vehcile
	 */
	Vehicle createVehicle();

}
