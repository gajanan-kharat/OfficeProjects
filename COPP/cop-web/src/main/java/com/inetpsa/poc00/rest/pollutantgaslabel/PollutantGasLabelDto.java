package com.inetpsa.poc00.rest.pollutantgaslabel;

import java.util.List;

/**
 * The Class PollutantGasLabelDto.
 */
public class PollutantGasLabelDto {

	/** The pollutant representation list. */
	List<PollutantGasLabelRepresentation> pollutantRepresentationList;

	/**
	 * Gets the pollutant representation list.
	 *
	 * @return the pollutant representation list
	 */
	public List<PollutantGasLabelRepresentation> getPollutantRepresentationList() {
		return pollutantRepresentationList;
	}

	/**
	 * Sets the pollutant representation list.
	 *
	 * @param pollutantRepresentationList the new pollutant representation list
	 */
	public void setPollutantRepresentationList(List<PollutantGasLabelRepresentation> pollutantRepresentationList) {
		this.pollutantRepresentationList = pollutantRepresentationList;
	}

}
