
package com.inetpsa.poc00.domain.generictestconditionmandatory;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface GenericTestCMFactory extends GenericFactory<GenericTestConditionMandatory> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the GenericTestConditionMandatory
     */
	GenericTestConditionMandatory createGenericTestCM();
	
}
