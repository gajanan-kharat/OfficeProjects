/*
 * Creation : Jan 4, 2017
 */
package com.inetpsa.poc00.application.fuel;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.fuel.Fuel;

/**
 * The Interface FuelService.
 */
@Service
public interface FuelService {

	/**
	 * Save fuel.
	 *
	 * @param fuel the fuel
	 * @return the fuel
	 */
	public Fuel saveFuel(Fuel fuel);

	/**
	 * Delete fuel.
	 *
	 * @param fuelId the fuel id
	 * @return true, if successful
	 */
	public boolean deleteFuel(Long fuelId);

}
