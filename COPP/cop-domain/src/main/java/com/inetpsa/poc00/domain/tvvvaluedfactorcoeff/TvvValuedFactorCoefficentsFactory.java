/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedfactorcoeff;

import org.seedstack.business.domain.GenericFactory;

import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * A factory for creating TvvValuedFactorCoefficents objects.
 */
public interface TvvValuedFactorCoefficentsFactory extends GenericFactory<TvvValuedFactorCoefficents> {

	/**
	 * Factory create method.
	 * 
	 * @return the FactorCoefficents
	 */
	TvvValuedFactorCoefficents createTvvValuedFactorCoefficents();

	/**
	 * Creates a new TvvValuedFactorCoefficents object.
	 * 
	 * @param defaultValue the default value
	 * @param fcLabel the fc label
	 * @param pollutantGasLabel the pollutant gas label
	 * @return the factor coefficents
	 */
	TvvValuedFactorCoefficents createTvvValuedFactorCoefficents(double defaultValue, FactorCoefficentsLabel fcLabel, PollutantGasLabel pollutantGasLabel);

	/**
	 * Creates a new TvvValuedFactorCoefficents object.
	 * 
	 * @param defaultValue the default value
	 * @param pgLabel the pg label
	 * @return the factor coefficents
	 */
	TvvValuedFactorCoefficents createTvvValuedFactorCoefficents(double defaultValue, PollutantGasLabel pgLabel);

	TvvValuedFactorCoefficents createTvvValuedFactorCoefficent(TvvValuedFactorCoefficents genericData);

}