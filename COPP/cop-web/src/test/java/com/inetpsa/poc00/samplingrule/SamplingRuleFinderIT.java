/*
 * Creation : Feb 3, 2017
 */
package com.inetpsa.poc00.samplingrule;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.samplingrule.SamplingRule;
import com.inetpsa.poc00.domain.samplingrule.SamplingRuleFactory;
import com.inetpsa.poc00.domain.samplingrule.SamplingRuleRepository;
import com.inetpsa.poc00.rest.sampling.SamplingRuleFinder;
import com.inetpsa.poc00.rest.sampling.SamplingRuleRepresentation;

@RunWith(SeedITRunner.class)
public class SamplingRuleFinderIT {

    @Inject
    SamplingRuleFactory samplingRuleFactory;

    @Inject
    SamplingRuleRepository samplingRuleRepo;

    @Inject
    SamplingRuleFinder samplingRuleFinder;

    @Test

    public void getAllSamplingRule() {
        SamplingRule samplingRule = samplingRuleFactory.createSamplingRule();
        samplingRuleRepo.saveSamplingRule(samplingRule);
        List<SamplingRuleRepresentation> samplingRuleRepresentList = samplingRuleFinder.getAllSamplingRule();
        assertNotNull(samplingRuleRepresentList);
    }
}
