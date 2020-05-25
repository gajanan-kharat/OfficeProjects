/*
 * Creation : Feb 14, 2017
 */
package com.inetpsa.poc00.rest.tvv;

import java.util.List;

import com.inetpsa.poc00.rest.sampling.SamplingRuleRepresentation;

public class SamplingRuleListRepresentation {
	private List<SamplingRuleRepresentation> samplingRuleRepresentationListTvv;
	private List<SamplingRuleRepresentation> samplingRuleRepresentationList;

	public List<SamplingRuleRepresentation> getSamplingRuleRepresentationListTvv() {
		return samplingRuleRepresentationListTvv;
	}

	public void setSamplingRuleRepresentationListTvv(List<SamplingRuleRepresentation> samplingRuleRepresentationListTvv) {
		this.samplingRuleRepresentationListTvv = samplingRuleRepresentationListTvv;
	}

	public List<SamplingRuleRepresentation> getSamplingRuleRepresentationList() {
		return samplingRuleRepresentationList;
	}

	public void setSamplingRuleRepresentationList(List<SamplingRuleRepresentation> samplingRuleRepresentationList) {
		this.samplingRuleRepresentationList = samplingRuleRepresentationList;
	}

}
