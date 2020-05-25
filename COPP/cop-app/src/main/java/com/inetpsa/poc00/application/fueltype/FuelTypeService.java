/*
 * Creation : Dec 29, 2016
 */
package com.inetpsa.poc00.application.fueltype;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.fueltype.FuelType;

/**
 * The Interface FuelTypeService.
 */
@Service
public interface FuelTypeService {

	/**
	 * Save fuel type.
	 * 
	 * @param fuelType the fuel type
	 * @return the fuel type
	 */
	public FuelType saveFuelType(FuelType fuelType);

	/**
	 * Delete fuel type.
	 * 
	 * @param fuelTypeId the fuel type id
	 * @return true, if successful
	 */
	public boolean deleteFuelType(Long fuelTypeId);
}
