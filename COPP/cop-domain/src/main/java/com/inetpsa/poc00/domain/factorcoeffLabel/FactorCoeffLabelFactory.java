package com.inetpsa.poc00.domain.factorcoeffLabel;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface FactorCoeffLabelFactory extends GenericFactory<FactorCoefficentsLabel> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the FactorCoefficentsLabel
	 */
	FactorCoefficentsLabel createFactorCoeffLabel();

	FactorCoefficentsLabel createFactorCoeffLabel(String label);

}
