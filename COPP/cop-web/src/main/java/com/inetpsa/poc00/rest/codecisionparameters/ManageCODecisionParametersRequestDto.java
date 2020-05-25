package com.inetpsa.poc00.rest.codecisionparameters;

import java.util.List;

/**
 * The Class ManageLambdaDecisionParametersRequestDto.
 */
public class ManageCODecisionParametersRequestDto {

	/** The CODecisionParametersRepresentation list. */
	private List<CODecisionParametersRepresentation> coDecisionParametersRepresentationsList;

	/**
	 * Gets the co decision parameters representations list.
	 *
	 * @return the co decision parameters representations list
	 */
	public List<CODecisionParametersRepresentation> getCoDecisionParametersRepresentationsList() {
		return coDecisionParametersRepresentationsList;
	}

	/**
	 * Sets the co decision parameters representations list.
	 *
	 * @param coDecisionParametersRepresentationsList the new co decision parameters representations list
	 */
	public void setCoDecisionParametersRepresentationsList(List<CODecisionParametersRepresentation> coDecisionParametersRepresentationsList) {
		this.coDecisionParametersRepresentationsList = coDecisionParametersRepresentationsList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ManageCODecisionParametersRequestDto [coDecisionParametersRepresentationsList=" + coDecisionParametersRepresentationsList + "]";
	}

}
