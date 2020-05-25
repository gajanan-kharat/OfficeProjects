package com.inetpsa.poc00.domain.pollutantgastestresult;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

/**
 * The Class PollutantGasTestResultFactoryImpl.
 */
public class PollutantGasTestResultFactoryImpl extends BaseFactory<PollutantGasTestResult> implements PollutantGasTestResultFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResultFactory#createPollutantGasTestResult()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PollutantGasTestResult createPollutantGasTestResult() {
		return new PollutantGasTestResult();
	}

}
