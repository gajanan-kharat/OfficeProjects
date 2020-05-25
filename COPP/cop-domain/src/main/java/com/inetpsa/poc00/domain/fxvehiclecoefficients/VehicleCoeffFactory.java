
package com.inetpsa.poc00.domain.fxvehiclecoefficients;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface VehicleCoeffFactory extends GenericFactory<VehicleCoefficents> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the VehicleCoefficents
     */
	VehicleCoefficents createVehicleCoefficents();
	
}
