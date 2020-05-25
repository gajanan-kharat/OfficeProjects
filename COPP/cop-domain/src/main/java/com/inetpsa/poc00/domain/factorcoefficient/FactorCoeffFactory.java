package com.inetpsa.poc00.domain.factorcoefficient;

import org.seedstack.business.domain.GenericFactory;

import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * A factory for creating FactorCoeff objects.
 */
public interface FactorCoeffFactory extends GenericFactory<FactorCoefficents> {

	/**
	 * Factory create method.
	 * 
	 * @return the FactorCoefficents
	 */
	FactorCoefficents createFactorCoefficents();

	/**
	 * Creates a new FactorCoeff object.
	 * 
	 * @param defaultValue the default value
	 * @param fcLabel the fc label
	 * @param pollutantGasLabel the pollutant gas label
	 * @return the factor coefficents
	 */
	FactorCoefficents createFactorCoefficents(double defaultValue, FactorCoefficentsLabel fcLabel, PollutantGasLabel pollutantGasLabel);

	/**
	 * Creates a new FactorCoeff object.
	 * 
	 * @param defaultValue the default value
	 * @param pgLabel the pg label
	 * @return the factor coefficents
	 */
	FactorCoefficents createFactorCoefficents(double defaultValue, PollutantGasLabel pgLabel);

}
