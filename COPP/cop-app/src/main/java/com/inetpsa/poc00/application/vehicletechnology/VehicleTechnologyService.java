/*
 * Creation : Jan 5, 2017
 */
/**
 * 
 */
package com.inetpsa.poc00.application.vehicletechnology;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;

/**
 * The VehicleTechnologyService.
 *
 * @author mehaj
 */
@Service
public interface VehicleTechnologyService {

	/**
	 * Save vehicle technology.
	 *
	 * @param vehicleTechnologyObj the vehicle technology obj
	 * @return the string
	 */
	public String saveVehicleTechnology(VehicleTechnology vehicleTechnologyObj);

	/**
	 * Delete vehicle technology.
	 *
	 * @param vehicleTechnologyId the vehicle technology id
	 * @return the string
	 */
	String deleteVehicleTechnology(long vehicleTechnologyId);
}
