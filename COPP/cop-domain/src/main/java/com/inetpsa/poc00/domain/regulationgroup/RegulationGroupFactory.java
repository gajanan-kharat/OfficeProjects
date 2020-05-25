
package com.inetpsa.poc00.domain.regulationgroup;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface RegulationGroupFactory extends GenericFactory<RegulationGroup> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the RegulationGroup
     */
	RegulationGroup createRegulationGroup();
	
}
