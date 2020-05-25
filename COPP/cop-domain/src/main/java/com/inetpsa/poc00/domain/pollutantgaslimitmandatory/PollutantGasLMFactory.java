
package com.inetpsa.poc00.domain.pollutantgaslimitmandatory;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface PollutantGasLMFactory extends GenericFactory<PollutantGasLmtMandatory> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the Pollutant Gas Limit Mandatory
     */
	PollutantGasLmtMandatory createPollGasLMandt();
	
}
