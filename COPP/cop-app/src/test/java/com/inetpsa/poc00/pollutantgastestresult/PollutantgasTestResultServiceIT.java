/*
 * Creation : Dec 9, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.pollutantgastestresult;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.application.pollutantgastestresult.PollutantgasTestResultService;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResultFactory;

/**
 * TODO : Description
 * 
 * @author mehaj
 */
@RunWith(SeedITRunner.class)
public class PollutantgasTestResultServiceIT {
    @Inject
    PollutantgasTestResultService pollutantgasTestResultService;
    @Inject
    PollutantGasTestResultFactory pollutantGasTestResultFactory;

    @Test
    public void savePollutantgasTestResultTest() {
        PollutantGasTestResult pollutantGasTestResult = pollutantGasTestResultFactory.createPollutantGasTestResult();
        PollutantGasTestResult pollutantGasTestResultSaved = pollutantgasTestResultService.savePollutantgasTestResult(pollutantGasTestResult);
        assertNotNull(pollutantGasTestResultSaved.getEntityId());
    }
}
