/*
 * Creation : Jan 5, 2017
 */
package com.inetpsa.poc00.application.vehiclecoefficent;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoefficents;

/**
 * The Interface VehicleCoeffService.
 */
@Service
public interface VehicleCoeffService {

	/**
	 * Save vehicle coefficents.
	 *
	 * @param vehicleCoeff the vehicle coeff
	 * @return
	 */
	public VehicleCoefficents saveVehicleCoefficents(VehicleCoefficents vehicleCoeff);

}
