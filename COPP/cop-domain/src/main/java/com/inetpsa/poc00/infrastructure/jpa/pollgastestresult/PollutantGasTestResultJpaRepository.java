/*
 * Creation : Nov 24, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.jpa.pollgastestresult;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResultRepository;

/**
 * The PollutantGasTestResultJpaRepository.
 *
 * @author mehaj
 */
public class PollutantGasTestResultJpaRepository extends BaseJpaRepository<PollutantGasTestResult, Long> implements PollutantGasTestResultRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResultRepository#savePollutantGasTestResult(com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult)
	 */
	@Override
	public PollutantGasTestResult savePollutantGasTestResult(PollutantGasTestResult object) {

		return super.save(object);
	}

}
