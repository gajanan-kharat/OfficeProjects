package com.inetpsa.poc00.rest.fuelinjectiontype;

import java.util.List;


/**
 * The Class FuelInjectionTypeDto.
 */
public class FuelInjectionTypeDto {

	/** The fuel injection type representation list. */
	List<FuelInjectionTypeRepresentation> fuelInjectionTypeRepresentationList;

	/**
	 * Gets the fuel injection type representation list.
	 *
	 * @return the fuel injection type representation list
	 */
	public List<FuelInjectionTypeRepresentation> getFuelInjectionTypeRepresentationList() {
		return fuelInjectionTypeRepresentationList;
	}

	/**
	 * Sets the fuel injection type representation list.
	 *
	 * @param fuelInjectionTypeRepresentationList the new fuel injection type representation list
	 */
	public void setFuelInjectionTypeRepresentationList(List<FuelInjectionTypeRepresentation> fuelInjectionTypeRepresentationList) {
		this.fuelInjectionTypeRepresentationList = fuelInjectionTypeRepresentationList;
	}

}
