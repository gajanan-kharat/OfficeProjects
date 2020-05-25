
package com.inetpsa.poc00.domain.fueltype;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface FuelTypeFactory extends GenericFactory<FuelType> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the FuelType
     */
	FuelType createFuelType();
	
}
