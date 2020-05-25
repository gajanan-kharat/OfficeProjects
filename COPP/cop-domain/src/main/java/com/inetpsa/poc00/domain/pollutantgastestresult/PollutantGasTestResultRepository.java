package com.inetpsa.poc00.domain.pollutantgastestresult;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface PollutantGasTestResultRepository.
 */
public interface PollutantGasTestResultRepository extends GenericRepository<PollutantGasTestResult, Long> {

	/**
	 * Save pollutant gas test result.
	 *
	 * @param object the object
	 * @return the pollutant gas test result
	 */
	PollutantGasTestResult savePollutantGasTestResult(PollutantGasTestResult object);
}
