package com.inetpsa.poc00.domain.pollutantgaslimit;

import org.seedstack.business.domain.GenericFactory;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * Category Factory interface.
 */

public interface PollutantGasLimitFactory extends GenericFactory<PollutantGasLimit> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the PollutantGasLimit
	 */
	PollutantGasLimit createPollutantGasLimit();

	PollutantGasLimit createPollutantGasLimits(PollutantGasLabel pollutantGasLabel, String maxDValRule, Double maxDValue, String minDValRule, Double minDValue);

}
