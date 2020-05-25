package com.inetpsa.poc00.rest.vehiclecoefficent;

import java.util.List;

/**
 * The Class VehicleCoeffDto.
 */
public class VehicleCoeffDto {

	/** The vehicle coeff representation list. */
	List<VehicleCoeffRepresentation> vehicleCoeffRepresentationList;

	/**
	 * Gets the vehicle coeff representation list.
	 * 
	 * @return the vehicle coeff representation list
	 */
	public List<VehicleCoeffRepresentation> getVehicleCoeffRepresentationList() {
		return vehicleCoeffRepresentationList;
	}

	/**
	 * Sets the vehicle coeff representation list.
	 * 
	 * @param vehicleCoeffRepresentationList the new vehicle coeff representation list
	 */
	public void setVehicleCoeffRepresentationList(List<VehicleCoeffRepresentation> vehicleCoeffRepresentationList) {
		this.vehicleCoeffRepresentationList = vehicleCoeffRepresentationList;
	}

}
