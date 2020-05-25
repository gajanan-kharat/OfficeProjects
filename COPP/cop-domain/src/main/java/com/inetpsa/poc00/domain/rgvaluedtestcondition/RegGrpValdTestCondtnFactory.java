
package com.inetpsa.poc00.domain.rgvaluedtestcondition;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface RegGrpValdTestCondtnFactory extends GenericFactory<RegGrpValdTestCondition> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the Regulation Group Valued Test Condition Object
     */
	RegGrpValdTestCondition createRegGrpValdTC();
	
}
