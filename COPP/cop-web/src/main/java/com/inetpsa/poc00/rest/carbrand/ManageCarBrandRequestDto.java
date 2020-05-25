package com.inetpsa.poc00.rest.carbrand;

import java.util.List;

/**
 * The Class ManageCarBrandRequestDto.
 */
public class ManageCarBrandRequestDto {

	/** The car brand representation list. */
	private List<CarBrandRepresentation> carBrandRepresentationList;

	/**
	 * Gets the car brand representation list.
	 * 
	 * @return the car brand representation list
	 */
	public List<CarBrandRepresentation> getCarBrandRepresentationList() {
		return carBrandRepresentationList;
	}

	/**
	 * Sets the car brand representation list.
	 * 
	 * @param carBrandRepresentationList the new car brand representation list
	 */
	public void setCarBrandRepresentationList(List<CarBrandRepresentation> carBrandRepresentationList) {
		this.carBrandRepresentationList = carBrandRepresentationList;
	}

}
