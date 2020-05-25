package com.inetpsa.poc00.rest.lambdadecisionparameters;

import java.util.List;

/**
 * The Class ManageLambdaDecisionParametersRequestDto.
 */
public class ManageLambdaDecisionParametersRequestDto {

	/** The LambdaDecisionParametersRepresentation representations list. */
	private List<LambdaDecisionParametersRepresentation> lambdaDecisionParametersRepresentationsList;

	/**
	 * Gets the lambda decision parameters representations list.
	 *
	 * @return the lambda decision parameters representations list
	 */
	public List<LambdaDecisionParametersRepresentation> getLambdaDecisionParametersRepresentationsList() {
		return lambdaDecisionParametersRepresentationsList;
	}

	/**
	 * Sets the lambda decision parameters representations list.
	 *
	 * @param lambdaDecisionParametersRepresentationsList the new lambda decision parameters representations list
	 */
	public void setLambdaDecisionParametersRepresentationsList(List<LambdaDecisionParametersRepresentation> lambdaDecisionParametersRepresentationsList) {
		this.lambdaDecisionParametersRepresentationsList = lambdaDecisionParametersRepresentationsList;
	}

}
