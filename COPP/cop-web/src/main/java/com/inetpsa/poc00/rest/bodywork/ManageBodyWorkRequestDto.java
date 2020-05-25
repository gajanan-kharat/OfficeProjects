package com.inetpsa.poc00.rest.bodywork;

import java.util.List;

/**
 * The Class ManageBodyWorkRequestDto.
 */
public class ManageBodyWorkRequestDto {

	/** The body work representation list. */
	private List<BodyWorkRepresentation> bodyWorkRepresentationList;

	/**
	 * Gets the body work representation list.
	 * 
	 * @return the body work representation list
	 */
	public List<BodyWorkRepresentation> getBodyWorkRepresentationList() {
		return bodyWorkRepresentationList;
	}

	/**
	 * Sets the body work representation list.
	 * 
	 * @param bodyWorkRepresentationList the new body work representation list
	 */
	public void setBodyWorkRepresentationList(List<BodyWorkRepresentation> bodyWorkRepresentationList) {
		this.bodyWorkRepresentationList = bodyWorkRepresentationList;
	}

}
