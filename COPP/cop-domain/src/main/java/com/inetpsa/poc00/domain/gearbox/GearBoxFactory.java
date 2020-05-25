
package com.inetpsa.poc00.domain.gearbox;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface GearBoxFactory extends GenericFactory<GearBox> {
    /**
     * Factory create method.
     *
     * @param name  
     * @return the GearBox
     */
	GearBox createGearBox();
	
}
