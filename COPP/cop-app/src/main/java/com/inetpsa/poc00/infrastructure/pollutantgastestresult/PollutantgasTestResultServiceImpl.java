/*
 * Creation : Nov 30, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.pollutantgastestresult;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.pollutantgastestresult.PollutantgasTestResultService;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResultRepository;

/**
 * PollutantgasTestResultServiceImpl.
 *
 * @author mehaj
 */
public class PollutantgasTestResultServiceImpl implements PollutantgasTestResultService {

	/** The pollutant gas test result repository. */
	@Inject
	PollutantGasTestResultRepository pollutantGasTestResultRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.pollutantgastestresult.PollutantgasTestResultService#savePollutantgasTestResult(com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PollutantGasTestResult savePollutantgasTestResult(PollutantGasTestResult pollutantgasTestResult) {
		return pollutantGasTestResultRepository.savePollutantGasTestResult(pollutantgasTestResult);
	}

}
