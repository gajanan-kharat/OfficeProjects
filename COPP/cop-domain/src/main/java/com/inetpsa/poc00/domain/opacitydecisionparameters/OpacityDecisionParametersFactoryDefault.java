
package com.inetpsa.poc00.domain.opacitydecisionparameters;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class OpacityDecisionParametersFactoryDefault.
 */
public class OpacityDecisionParametersFactoryDefault extends BaseFactory<OpacityDecisionParameters> implements OpacityDecisionParametersFactory {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersFactory#createOpacityDecisionParameters()
     */
    @Override
    public OpacityDecisionParameters createOpacityDecisionParameters() {

        return new OpacityDecisionParameters();
    }

}
