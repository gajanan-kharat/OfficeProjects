
package com.inetpsa.poc00.domain.technicalcase;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface TechCaseFactory extends GenericFactory<TechnicalCase> {
    /**
     * Factory create method.
     *
     * @param name
     * @return the TechnicalCase
     */
	TechnicalCase createTechCase();
	
}
