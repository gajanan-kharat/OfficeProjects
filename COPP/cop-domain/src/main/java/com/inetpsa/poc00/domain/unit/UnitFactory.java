
package com.inetpsa.poc00.domain.unit;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface UnitFactory extends GenericFactory<Unit> {
    /**
     * Factory create method.
     *
     * @param name
     * @return the Unit
     */
	Unit createUnit();
	
}
