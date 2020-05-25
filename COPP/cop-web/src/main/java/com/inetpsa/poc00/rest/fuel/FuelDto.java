package com.inetpsa.poc00.rest.fuel;

import java.util.List;


/**
 * The Class FuelDto.
 */
public class FuelDto {

	/** The fuel representation list. */
	List<FuelRepresentation> fuelRepresentationList;

	/**
	 * Gets the fuel representation list.
	 *
	 * @return the fuel representation list
	 */
	public List<FuelRepresentation> getFuelRepresentationList() {
		return fuelRepresentationList;
	}

	/**
	 * Sets the fuel representation list.
	 *
	 * @param fuelRepresentationList the new fuel representation list
	 */
	public void setFuelRepresentationList(List<FuelRepresentation> fuelRepresentationList) {
		this.fuelRepresentationList = fuelRepresentationList;
	}

}
