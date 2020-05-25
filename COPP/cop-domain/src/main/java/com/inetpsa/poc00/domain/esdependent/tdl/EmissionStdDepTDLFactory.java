package com.inetpsa.poc00.domain.esdependent.tdl;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface EmissionStdDepTDLFactory extends GenericFactory<EmissionStdDepTDL> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the EmissionStdDepTDL
	 */
	EmissionStdDepTDL createEmissonStdDepTDL();

	EmissionStdDepTDL createEmissonStdDepTDL(String label, String description, String version);

}
