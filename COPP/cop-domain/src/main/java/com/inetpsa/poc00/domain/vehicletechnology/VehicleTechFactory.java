
package com.inetpsa.poc00.domain.vehicletechnology;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface VehicleTechFactory extends GenericFactory<VehicleTechnology> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the VehicleTechnology
     */
	VehicleTechnology createVehTechnology();
	
}
