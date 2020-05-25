
package com.inetpsa.poc00.domain.fuel;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface FuelFactory extends GenericFactory<Fuel> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the Fuel
     */
	Fuel createFuel();
	
}
