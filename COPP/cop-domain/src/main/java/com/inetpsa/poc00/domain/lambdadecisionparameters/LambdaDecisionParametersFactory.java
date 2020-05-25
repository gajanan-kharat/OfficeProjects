
package com.inetpsa.poc00.domain.lambdadecisionparameters;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating LambdaDecisionParameters objects.
 */
public interface LambdaDecisionParametersFactory extends GenericFactory<LambdaDecisionParameters> {

    /**
     * Creates a new LambdaDecisionParameters object.
     *
     * @return the lambda decision parameters
     */
    LambdaDecisionParameters createLambdaDecisionParameters();

}
