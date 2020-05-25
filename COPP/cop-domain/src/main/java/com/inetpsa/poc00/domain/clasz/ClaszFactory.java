
package com.inetpsa.poc00.domain.clasz;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface ClaszFactory extends GenericFactory<Clasz> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the Class
     */
	Clasz createClasz();
	
}
