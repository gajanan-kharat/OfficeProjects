package com.inetpsa.poc00.rest.vehicletechnology;

import java.util.List;

/**
 * The Class VehicleTechnologyDto.
 */
public class VehicleTechnologyDto {

	/** The vechicle technology representation list. */
	List<VehicleTechnologyRepresentation> vechicleTechnologyRepresentationList;

	/**
	 * Gets the vechicle technology representation list.
	 * 
	 * @return the vechicle technology representation list
	 */
	public List<VehicleTechnologyRepresentation> getVechicleTechnologyRepresentationList() {
		return vechicleTechnologyRepresentationList;
	}

	/**
	 * Sets the vechicle technology representation list.
	 * 
	 * @param vechicleTechnologyRepresentationList the new vechicle technology representation list
	 */
	public void setVechicleTechnologyRepresentationList(List<VehicleTechnologyRepresentation> vechicleTechnologyRepresentationList) {
		this.vechicleTechnologyRepresentationList = vechicleTechnologyRepresentationList;
	}

}
