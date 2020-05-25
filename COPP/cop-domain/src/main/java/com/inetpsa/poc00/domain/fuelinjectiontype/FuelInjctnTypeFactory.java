
package com.inetpsa.poc00.domain.fuelinjectiontype;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface FuelInjctnTypeFactory extends GenericFactory<FuelInjectionType> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the FuelInjectionType
     */
	FuelInjectionType createFuelInjctnType();
	
}
