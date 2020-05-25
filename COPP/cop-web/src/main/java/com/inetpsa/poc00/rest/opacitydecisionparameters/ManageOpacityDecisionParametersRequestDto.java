package com.inetpsa.poc00.rest.opacitydecisionparameters;

import java.util.List;

/**
 * The Class ManageLambdaDecisionParametersRequestDto.
 */
public class ManageOpacityDecisionParametersRequestDto {

	/** The opacity decision parameters representations list. */
	private List<OpacityDecisionParametersRepresentation> opacityDecisionParametersRepresentationsList;

	/**
	 * Gets the opacity decision parameters representations list.
	 *
	 * @return the opacity decision parameters representations list
	 */
	public List<OpacityDecisionParametersRepresentation> getOpacityDecisionParametersRepresentationsList() {
		return opacityDecisionParametersRepresentationsList;
	}

	/**
	 * Sets the opacity decision parameters representations list.
	 *
	 * @param opacityDecisionParametersRepresentationsList the new opacity decision parameters representations list
	 */
	public void setOpacityDecisionParametersRepresentationsList(List<OpacityDecisionParametersRepresentation> opacityDecisionParametersRepresentationsList) {
		this.opacityDecisionParametersRepresentationsList = opacityDecisionParametersRepresentationsList;
	}

}
