
package com.inetpsa.poc00.domain.factory;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface CarFactoryObjectCreation extends GenericFactory<CarFactory> {
    /**
     * Factory create method.
	 * 
	 * @param name
     * @return the factory
     */
	CarFactory createCarFactoryObject();

	CarFactory createCarFactoryObject(Long entityId, String label);

}
