package com.inetpsa.poc00.domain.generictc;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface GenericTestConditionFactory extends GenericFactory<GenericTestCondition> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the GenericTestCondition
	 */
	GenericTestCondition createGenericTestCondtn();

	GenericTestCondition createGenericTestCondition(String dataType, String defaultValue, String label);

	GenericTestCondition createGenericTestCondition(String dataType, String defaultValue, String label, String value);

}
