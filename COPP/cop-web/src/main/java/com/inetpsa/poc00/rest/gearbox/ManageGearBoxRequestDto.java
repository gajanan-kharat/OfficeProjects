package com.inetpsa.poc00.rest.gearbox;

import java.util.List;


/**
 * The Class ManageGearBoxRequestDto.
 */
public class ManageGearBoxRequestDto {

	/** The gear box representation list. */
	private List<GearBoxRepresentation> gearBoxRepresentationList;

	/**
	 * Gets the gear box representation list.
	 *
	 * @return the gear box representation list
	 */
	public List<GearBoxRepresentation> getGearBoxRepresentationList() {
		return gearBoxRepresentationList;
	}

	/**
	 * Sets the gear box representation list.
	 *
	 * @param gearBoxRepresentationList the new gear box representation list
	 */
	public void setGearBoxRepresentationList(List<GearBoxRepresentation> gearBoxRepresentationList) {
		this.gearBoxRepresentationList = gearBoxRepresentationList;
	}

}
