/*
 * Creation : Jan 4, 2017
 */
package com.inetpsa.poc00.application.samplingrule;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.samplingrule.SamplingRule;

@Service
public interface SamplingRuleService {

    public SamplingRule saveSamplingRule(SamplingRule samplingRule);

}
