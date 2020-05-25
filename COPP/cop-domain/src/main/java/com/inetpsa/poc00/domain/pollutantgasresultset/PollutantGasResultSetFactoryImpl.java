package com.inetpsa.poc00.domain.pollutantgasresultset;

import javax.transaction.Transactional;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Class PollutantGasResultSetFactoryImpl.
 */
public class PollutantGasResultSetFactoryImpl extends BaseFactory<PollutantGasResultSet> implements PollutantGasResultSetFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetFactory#createPollutantGasResultSet()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PollutantGasResultSet createPollutantGasResultSet() {
		return new PollutantGasResultSet();
	}

}
