/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedpglimit;

import org.seedstack.business.domain.GenericFactory;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * A factory for creating TvvValuedPollutantGasLimit objects.
 */
public interface TvvValuedPollutantGasLimitFactory extends GenericFactory<TvvValuedPollutantGasLimit> {

	/**
	 * Factory create method.
	 * 
	 * @return the PollutantGasLimit
	 */
	TvvValuedPollutantGasLimit createTvvValuedPollutantGasLimit();

	/**
	 * Creates a new TvvValuedPollutantGasLimit object.
	 * 
	 * @param pollutantGasLabel the pollutant gas label
	 * @param maxDValRule the max d val rule
	 * @param maxDValue the max d value
	 * @param minDValRule the min d val rule
	 * @param minDValue the min d value
	 * @return the tvv valued pollutant gas limit
	 */
	TvvValuedPollutantGasLimit createTvvValuedPollutantGasLimit(PollutantGasLabel pollutantGasLabel, String maxDValRule, Double maxDValue, String minDValRule, Double minDValue);

	/**
	 * Creates a new TvvValuedPollutantGasLimit object.
	 * 
	 * @param pollutantGasLimit the generic data
	 * @return the tvv valued pollutant gas limit
	 */
	TvvValuedPollutantGasLimit createTvvValuedPollutantGasLimit(TvvValuedPollutantGasLimit pollutantGasLimit);

}