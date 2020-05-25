
package com.inetpsa.poc00.domain.rgvaluedesdependenttcl;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface RGValuedESDependentTCLFactory extends GenericFactory<RGValuedESDependentTCL> {
    /**
     * Factory create method.
     *
     * @param name  
     * @return the Regulation Group Valued Emission Standard Dependent Test Condition List
     */
	RGValuedESDependentTCL createRegGrpValuedESDTCL();
	
}
