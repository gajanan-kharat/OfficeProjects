package com.inetpsa.poc00.domain.preparationfile;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating PreparationFile objects.
 */
public interface PreparationFileFactory extends GenericFactory<PreparationFile> {

	/**
	 * Creates a new PreparationFile object.
	 * 
	 * @return the preparation file
	 */
	PreparationFile createPreparationFile();

}
