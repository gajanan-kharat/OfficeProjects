package com.inetpsa.poc00.rest.fueltype;

import java.util.List;


/**
 * The Class ManageFuelTypeRequestDto.
 */
public class ManageFuelTypeRequestDto {

	/** The fuel type representation list. */
	private List<FuelTypeRepresentation> fuelTypeRepresentationList;

	/**
	 * Gets the fuel type representation list.
	 *
	 * @return the fuel type representation list
	 */
	public List<FuelTypeRepresentation> getFuelTypeRepresentationList() {
		return fuelTypeRepresentationList;
	}

	/**
	 * Sets the fuel type representation list.
	 *
	 * @param fuelTypeRepresentationList the new fuel type representation list
	 */
	public void setFuelTypeRepresentationList(List<FuelTypeRepresentation> fuelTypeRepresentationList) {
		this.fuelTypeRepresentationList = fuelTypeRepresentationList;
	}
}
