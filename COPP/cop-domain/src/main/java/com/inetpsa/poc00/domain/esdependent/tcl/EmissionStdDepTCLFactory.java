package com.inetpsa.poc00.domain.esdependent.tcl;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface EmissionStdDepTCLFactory extends GenericFactory<EmissionStdDepTCL> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the EmissionStdDepTCL
	 */

	EmissionStdDepTCL createEmissonStdDepTCL();

	EmissionStdDepTCL createEmissonStdDepTCL(String label, String description, String version);

}
