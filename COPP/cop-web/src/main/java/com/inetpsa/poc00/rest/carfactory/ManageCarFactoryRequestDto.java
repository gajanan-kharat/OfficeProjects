package com.inetpsa.poc00.rest.carfactory;

import java.util.List;

/**
 * The Class ManageCarFactoryRequestDto.
 */
public class ManageCarFactoryRequestDto {

	/** The car factory representation list. */
	private List<CarFactoryRepresentation> carFactoryRepresentationList;

	/**
	 * Gets the car factory representation list.
	 * 
	 * @return the car factory representation list
	 */
	public List<CarFactoryRepresentation> getCarFactoryRepresentationList() {
		return carFactoryRepresentationList;
	}

	/**
	 * Sets the car factory representation list.
	 * 
	 * @param carFactoryRepresentationList the new car factory representation list
	 */
	public void setCarFactoryRepresentationList(List<CarFactoryRepresentation> carFactoryRepresentationList) {
		this.carFactoryRepresentationList = carFactoryRepresentationList;
	}

}
