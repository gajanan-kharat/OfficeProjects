package com.inetpsa.poc00.rest.receptionfile;

import java.util.List;

/**
 * The Class ManageReceptionFileRequestDto.
 */
public class ManageReceptionFileRequestDto {

	/** The reception file representations list. */
	private List<ReceptionFileRepresentation> receptionFileRepresentationsList;

	/**
	 * Gets the reception file representations list.
	 *
	 * @return the reception file representations list
	 */
	public List<ReceptionFileRepresentation> getReceptionFileRepresentationsList() {
		return receptionFileRepresentationsList;
	}

	/**
	 * Sets the reception file representations list.
	 *
	 * @param receptionFileRepresentationsList the new reception file representations list
	 */
	public void setReceptionFileRepresentationsList(List<ReceptionFileRepresentation> receptionFileRepresentationsList) {
		this.receptionFileRepresentationsList = receptionFileRepresentationsList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ManageReceptionFileRequestDto [receptionFileRepresentationsList=" + receptionFileRepresentationsList + "]";
	}

}
