package com.inetpsa.poc00.domain.vehicle;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class VehicleFactoryImpl.
 */
public class VehicleFactoryImpl extends BaseFactory<Vehicle> implements VehicleFactory {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.vehicle.VehicleFactory#createVehicle()
	 */
	@Override
	public Vehicle createVehicle() {
		return new Vehicle();

	}

}
