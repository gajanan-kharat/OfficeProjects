
package com.inetpsa.poc00.domain.codecisionparameters;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating CODecisionParameters objects.
 */
public interface CODecisionParametersFactory extends GenericFactory<CODecisionParameters> {

    /**
     * Creates a new CODecisionParameters object.
     *
     * @return the CO decision parameters
     */
    CODecisionParameters createCODecisionParameters();

}
