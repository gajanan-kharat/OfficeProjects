/*
 * Creation : Nov 30, 2016
 */
package com.inetpsa.poc00.application.pollutantgastestresult;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;

/**
 * The Interface PollutantgasTestResultService.
 * 
 * @author mehaj
 */
@Service
public interface PollutantgasTestResultService {

	/**
	 * Save pollutantgas test result.
	 *
	 * @param pollutantgasTestResult the pollutantgas test result
	 * @return the pollutant gas test result
	 */
	PollutantGasTestResult savePollutantgasTestResult(PollutantGasTestResult pollutantgasTestResult);
}
