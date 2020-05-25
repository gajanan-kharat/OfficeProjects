package com.inetpsa.poc00.rest.coastdown;

import java.util.List;


/**
 * The Class CoastdownDto.
 */
public class CoastdownDto {

	/** The costdown representation list. */
	List<CoastdownRepresentation> costdownRepresentationList;

	/**
	 * Gets the costdown representation list.
	 *
	 * @return the costdown representation list
	 */
	public List<CoastdownRepresentation> getCostdownRepresentationList() {
		return costdownRepresentationList;
	}

	/**
	 * Sets the costdown representation list.
	 *
	 * @param costdownRepresentationList the new costdown representation list
	 */
	public void setCostdownRepresentationList(List<CoastdownRepresentation> costdownRepresentationList) {
		this.costdownRepresentationList = costdownRepresentationList;
	}

}
