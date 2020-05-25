package com.inetpsa.poc00.domain.pollutantgaslimitlist;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface PollutantGasLimitListFactory extends GenericFactory<PollutantGasLimitList> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the PollutantGasLimitList
	 */
	PollutantGasLimitList createPollutantGasLimitList();

	PollutantGasLimitList createPollutantGasLimitList(String label, String description, String version);

}
