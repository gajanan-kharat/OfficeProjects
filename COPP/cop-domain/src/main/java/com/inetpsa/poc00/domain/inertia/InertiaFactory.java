
package com.inetpsa.poc00.domain.inertia;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface InertiaFactory extends GenericFactory<Inertia> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the Inertia
     */
	Inertia createInertia();
	
}
