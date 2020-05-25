
package com.inetpsa.poc00.domain.bodywork;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface BodyworkFactory extends GenericFactory<Bodywork> {
    /**
     * Factory create method.
     *
     * 
     */
	Bodywork createBodywork();
	
}
