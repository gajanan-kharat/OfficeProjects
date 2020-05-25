/*
 * Creation : Jan 4, 2017
 */
package com.inetpsa.poc00.infrastructure.samplingrule;

import javax.inject.Inject;

import com.inetpsa.poc00.application.samplingrule.SamplingRuleService;
import com.inetpsa.poc00.domain.samplingrule.SamplingRule;
import com.inetpsa.poc00.domain.samplingrule.SamplingRuleRepository;

public class SamplingRuleServiceImpl implements SamplingRuleService {

    @Inject
    private SamplingRuleRepository samplingrRepository;

    @Override
    public SamplingRule saveSamplingRule(SamplingRule samplingRule) {

        return samplingrRepository.saveSamplingRule(samplingRule);

    }

}
