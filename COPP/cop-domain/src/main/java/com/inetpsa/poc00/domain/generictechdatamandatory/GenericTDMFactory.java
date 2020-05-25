
package com.inetpsa.poc00.domain.generictechdatamandatory;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface GenericTDMFactory extends GenericFactory<GenericTechDataMandatory> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the GenericTechDataMandatory
     */
	GenericTechDataMandatory createGenericTDM();
	
}
