
package com.inetpsa.poc00.domain.pollutantgaslabel;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface PollutantGasLabelFactory extends GenericFactory<PollutantGasLabel> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the PollutantGasLabel
     */
	

	PollutantGasLabel createPollutantGasLabel();
	
}
