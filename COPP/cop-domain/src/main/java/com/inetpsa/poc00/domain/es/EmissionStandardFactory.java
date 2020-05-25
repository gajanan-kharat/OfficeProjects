package com.inetpsa.poc00.domain.es;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface EmissionStandardFactory extends GenericFactory<EmissionStandard> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the EmissionStandard
	 */
	EmissionStandard createEmissonStandard();

	EmissionStandard createEmissonStandard(String esLabel, String description, String version);

}
