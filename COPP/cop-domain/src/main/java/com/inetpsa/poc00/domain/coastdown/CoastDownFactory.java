
package com.inetpsa.poc00.domain.coastdown;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface CoastDownFactory extends GenericFactory<CoastDown> {
    /**
     * Factory create method.
     *
     * @param name  
     * @return the CoastDown
     */
	CoastDown createCoastDown();
	
}
