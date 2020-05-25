package com.inetpsa.poc00.domain.pollutantgastestresult;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating PollutantGasTestResult objects.
 */
public interface PollutantGasTestResultFactory extends GenericFactory<PollutantGasTestResult> {

	/**
	 * Creates a new PollutantGasTestResult object.
	 *
	 * @return the pollutant gas test result
	 */
	PollutantGasTestResult createPollutantGasTestResult();
}
