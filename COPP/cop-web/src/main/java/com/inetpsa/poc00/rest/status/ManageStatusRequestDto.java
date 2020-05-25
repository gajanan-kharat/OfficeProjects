package com.inetpsa.poc00.rest.status;

import java.util.List;

/**
 * The Class ManageStatusRequestDto.
 */
public class ManageStatusRequestDto {

	/** The status representation list. */
	private List<StatusRepresentation> statusRepresentationList;

	/**
	 * Gets the status representation list.
	 * 
	 * @return the status representation list
	 */
	public List<StatusRepresentation> getStatusRepresentationList() {
		return statusRepresentationList;
	}

	/**
	 * Sets the status representation list.
	 * 
	 * @param statusRepresentationList the new status representation list
	 */
	public void setStatusRepresentationList(List<StatusRepresentation> statusRepresentationList) {
		this.statusRepresentationList = statusRepresentationList;
	}

}
