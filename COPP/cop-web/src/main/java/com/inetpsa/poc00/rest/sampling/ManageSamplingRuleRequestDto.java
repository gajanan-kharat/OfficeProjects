package com.inetpsa.poc00.rest.sampling;

import java.util.List;

public class ManageSamplingRuleRequestDto {
	private List<SamplingRuleRepresentation> samplingRuleRepresentation;

	/**
	 * @return the samplingRuleRepresentation
	 */
	public List<SamplingRuleRepresentation> getSamplingRuleRepresentation() {
		return samplingRuleRepresentation;
	}

	/**
	 * @param samplingRuleRepresentation the samplingRuleRepresentation to set
	 */
	public void setSamplingRuleRepresentation(List<SamplingRuleRepresentation> samplingRuleRepresentation) {
		this.samplingRuleRepresentation = samplingRuleRepresentation;
	}
}
