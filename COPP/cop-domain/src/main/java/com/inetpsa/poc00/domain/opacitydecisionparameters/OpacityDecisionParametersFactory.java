
package com.inetpsa.poc00.domain.opacitydecisionparameters;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating OpacityDecisionParameters objects.
 */
public interface OpacityDecisionParametersFactory extends GenericFactory<OpacityDecisionParameters> {

    /**
     * Creates a new OpacityDecisionParameters object.
     *
     * @return the opacity decision parameters
     */
    OpacityDecisionParameters createOpacityDecisionParameters();

}
