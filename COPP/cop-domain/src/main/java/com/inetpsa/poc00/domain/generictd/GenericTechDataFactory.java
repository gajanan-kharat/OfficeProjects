package com.inetpsa.poc00.domain.generictd;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface GenericTechDataFactory extends GenericFactory<GenericTechnicalData> {
	/**
	 * Factory create method.
	 * 
	 * @param name the dictionary name
	 * @return the dictionary
	 */
	GenericTechnicalData createGenericTechData();

	GenericTechnicalData createGenericTechData(String dataType, String defaultValue, String label, String unitValue);

	GenericTechnicalData createGenericTechData(String dataType, String defaultValue, String label);

}
