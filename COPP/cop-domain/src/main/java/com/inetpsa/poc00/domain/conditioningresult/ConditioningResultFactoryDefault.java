/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.conditioningresult;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class ConditioningResultFactoryImpl.
 */
public class ConditioningResultFactoryDefault extends BaseFactory<ConditioningResult> implements ConditioningResultFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.conditioningresult.ConditioningResultFactory#createConditioningResult()
	 */
	@Override
	public ConditioningResult createConditioningResult() {

		return new ConditioningResult();
	}

}
