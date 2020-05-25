
package com.inetpsa.poc00.domain.lambdadecisionparameters;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class LambdaDecisionParametersFactoryDefault.
 */
public class LambdaDecisionParametersFactoryDefault extends BaseFactory<LambdaDecisionParameters> implements LambdaDecisionParametersFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersFactory#createLambdaDecisionParameters()
	 */
	@Override
	public LambdaDecisionParameters createLambdaDecisionParameters() {

		return new LambdaDecisionParameters();
	}

}
