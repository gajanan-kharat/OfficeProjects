/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.specialtestcondition;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class SpecialTestConditionFactoryDefault.
 */
public class SpecialTestConditionFactoryDefault extends BaseFactory<SpecialTestCondition> implements SpecialTestConditionFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.specialtestcondition.SpecialTestConditionFactory#createSpecialTestCondition()
	 */
	@Override
	public SpecialTestCondition createSpecialTestCondition() {
		return new SpecialTestCondition();
	}

}
