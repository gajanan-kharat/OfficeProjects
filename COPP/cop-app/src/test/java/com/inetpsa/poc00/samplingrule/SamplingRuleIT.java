/*
 * Creation : Jan 10, 2017
 */
package com.inetpsa.poc00.samplingrule;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.application.samplingrule.SamplingRuleService;
import com.inetpsa.poc00.domain.samplingrule.SamplingRule;
import com.inetpsa.poc00.domain.samplingrule.SamplingRuleFactory;
import com.inetpsa.poc00.domain.samplingrule.SamplingRuleRepository;

@RunWith(SeedITRunner.class)
public class SamplingRuleIT {

    @Inject
    SamplingRuleFactory samplingRuleFactory;

    @Inject
    SamplingRuleRepository samplingRuleRepository;

    @Inject
    SamplingRuleService samplingRuleService;

    @Test
    public void saveSamplingRule() {
        SamplingRule samplingrule = samplingRuleFactory.createSamplingRule();
        samplingrule.setLabel("samplingLabel");
        SamplingRule newObj = samplingRuleService.saveSamplingRule(samplingrule);
        SamplingRule loadedObj = samplingRuleRepository.loadSamplingRule(newObj.getEntityId());
        assertNotNull(loadedObj);

    }

}
