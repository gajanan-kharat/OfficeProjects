package com.inetpsa.poc00.domain.pollutantgasresultset;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating PollutantGasResultSet objects.
 */
public interface PollutantGasResultSetFactory extends GenericFactory<PollutantGasResultSet> {

	/**
	 * Creates a new PollutantGasResultSet object.
	 *
	 * @return the pollutant gas result set
	 */
	PollutantGasResultSet createPollutantGasResultSet();
}
