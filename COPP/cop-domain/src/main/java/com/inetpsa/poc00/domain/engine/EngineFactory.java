
package com.inetpsa.poc00.domain.engine;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface EngineFactory extends GenericFactory<Engine> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the Engine
     */
	Engine createEngine();
	
}
