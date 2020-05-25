/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedfactorcoeff;

import org.seedstack.business.domain.BaseFactory;

import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * The Class TvvValuedFactorCoefficentsFactoryDefault.
 */
public class TvvValuedFactorCoefficentsFactoryDefault extends BaseFactory<TvvValuedFactorCoefficents> implements TvvValuedFactorCoefficentsFactory {

	/**
	 * Factory create method.
	 * 
	 * @return the FactorCoefficents
	 */
	@Override
	public TvvValuedFactorCoefficents createTvvValuedFactorCoefficents() {
		return new TvvValuedFactorCoefficents();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficentsFactory#createTvvValuedFactorCoefficents(double,
	 *      com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel,
	 *      com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel)
	 */
	@Override
	public TvvValuedFactorCoefficents createTvvValuedFactorCoefficents(double defaultValue, FactorCoefficentsLabel fcLabel, PollutantGasLabel pollutantGasLabel) {
		return new TvvValuedFactorCoefficents(defaultValue, fcLabel, pollutantGasLabel);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficentsFactory#createTvvValuedFactorCoefficents(double,
	 *      com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel)
	 */
	@Override
	public TvvValuedFactorCoefficents createTvvValuedFactorCoefficents(double defaultValue, PollutantGasLabel pgLabel) {
		return new TvvValuedFactorCoefficents(defaultValue, pgLabel);
	}

	@Override
	public TvvValuedFactorCoefficents createTvvValuedFactorCoefficent(TvvValuedFactorCoefficents tvvValuedFactorCoefficents) {
		return new TvvValuedFactorCoefficents(tvvValuedFactorCoefficents);
	}

}
