
package com.inetpsa.poc00.domain.finalreductionratio;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface FinalReductionRatioFactory extends GenericFactory<FinalReductionRatio> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the FinalReductionRatio
     */
	FinalReductionRatio createReductionRatio();
	
}
