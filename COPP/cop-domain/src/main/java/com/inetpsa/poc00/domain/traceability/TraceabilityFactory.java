
package com.inetpsa.poc00.domain.traceability;

import org.seedstack.business.domain.GenericFactory;


/**
 * Traceability Factory interface.
 */

public interface TraceabilityFactory extends GenericFactory<Traceability> {
    
	/**
     * Factory create method.
     *
     * @param name   the dictionary name
     * @return the dictionary
     */
	Traceability createTraceability();
	
}
