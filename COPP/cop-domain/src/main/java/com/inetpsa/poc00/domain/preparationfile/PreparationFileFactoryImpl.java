package com.inetpsa.poc00.domain.preparationfile;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class PreparationFileFactoryImpl.
 */
public class PreparationFileFactoryImpl extends BaseFactory<PreparationFile> implements PreparationFileFactory {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.preparationfile.PreparationFileFactory#createPreparationFile()
	 */
	@Override
	public PreparationFile createPreparationFile() {
		return new PreparationFile();
	}

}
