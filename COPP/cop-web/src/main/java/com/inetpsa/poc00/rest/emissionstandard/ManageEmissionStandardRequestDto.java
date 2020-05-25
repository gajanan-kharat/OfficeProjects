package com.inetpsa.poc00.rest.emissionstandard;

import java.util.List;


/**
 * The Class ManageEmissionStandardRequestDto.
 */
public class ManageEmissionStandardRequestDto {
	
	/** The emisssion standard representation list. */
	private List<EmissionStandardRepresentation> emisssionStandardRepresentationList;

	/**
	 * Gets the emisssion standard representation list.
	 *
	 * @return the emisssion standard representation list
	 */
	public List<EmissionStandardRepresentation> getEmisssionStandardRepresentationList() {
		return emisssionStandardRepresentationList;
	}

	/**
	 * Sets the emisssion standard representation list.
	 *
	 * @param emisssionStandardRepresentationList the new emisssion standard representation list
	 */
	public void setEmisssionStandardRepresentationList(
			List<EmissionStandardRepresentation> emisssionStandardRepresentationList) {
		this.emisssionStandardRepresentationList = emisssionStandardRepresentationList;
	}

}
