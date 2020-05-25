
package com.inetpsa.poc00.domain.samplingrule;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface SamplingRuleFactory extends GenericFactory<SamplingRule> {
    /**
     * Factory create method.
     *
     * @param name
     * @return the SamplingRule
     */
	SamplingRule createSamplingRule();
	
}
