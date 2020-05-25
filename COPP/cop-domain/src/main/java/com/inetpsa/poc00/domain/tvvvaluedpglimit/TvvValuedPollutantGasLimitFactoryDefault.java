/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedpglimit;

import org.seedstack.business.domain.BaseFactory;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * The Class TvvValuedPollutantGasLimitFactoryDefault.
 */
public class TvvValuedPollutantGasLimitFactoryDefault extends BaseFactory<TvvValuedPollutantGasLimit> implements TvvValuedPollutantGasLimitFactory {

	/**
	 * Factory create method.
	 * 
	 * @return the PollutantGasLimit
	 */
	@Override
	public TvvValuedPollutantGasLimit createTvvValuedPollutantGasLimit() {
		return new TvvValuedPollutantGasLimit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimitFactory#createTvvValuedPollutantGasLimit(com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel,
	 *      java.lang.String, java.lang.Double, java.lang.String, java.lang.Double)
	 */
	@Override
	public TvvValuedPollutantGasLimit createTvvValuedPollutantGasLimit(PollutantGasLabel pollutantGasLabel, String maxDValRule, Double maxDValue, String minDValRule, Double minDValue) {
		return new TvvValuedPollutantGasLimit(pollutantGasLabel, maxDValRule, maxDValue, minDValRule, minDValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimitFactory#createTvvValuedPollutantGasLimit(com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit)
	 */
	@Override
	public TvvValuedPollutantGasLimit createTvvValuedPollutantGasLimit(TvvValuedPollutantGasLimit pollutantGasLimit) {
		return new TvvValuedPollutantGasLimit(pollutantGasLimit);
	}
}