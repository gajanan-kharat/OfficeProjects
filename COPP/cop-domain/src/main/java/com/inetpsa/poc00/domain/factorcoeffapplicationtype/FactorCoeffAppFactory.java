package com.inetpsa.poc00.domain.factorcoeffapplicationtype;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface FactorCoeffAppFactory extends GenericFactory<FactorCoeffApplicationType> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the FactorCoefficentsApplicationType
	 */
	FactorCoeffApplicationType createFactorCoefficents();

}
