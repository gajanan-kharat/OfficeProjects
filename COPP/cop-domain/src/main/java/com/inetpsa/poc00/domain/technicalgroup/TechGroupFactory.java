
package com.inetpsa.poc00.domain.technicalgroup;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface TechGroupFactory extends GenericFactory<TechnicalGroup> {
    /**
     * Factory create method.
     *
     * @param name
     * @return the TechnicalGroup
     */
	TechnicalGroup createTechGroup();
	
}
