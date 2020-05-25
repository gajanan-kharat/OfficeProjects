
package com.inetpsa.poc00.domain.projectcodefamily;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface ProjectCodeFamilyFactory extends GenericFactory<ProjectCodeFamily> {
    /**
     * Factory create method.
     *
     * @param name  
     * @return the ProjectCodeFamily
     */
	ProjectCodeFamily createPCFamily();
	
}
