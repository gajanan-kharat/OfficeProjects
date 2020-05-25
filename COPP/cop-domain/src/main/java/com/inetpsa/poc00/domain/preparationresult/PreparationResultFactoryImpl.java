package com.inetpsa.poc00.domain.preparationresult;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class PreparationResultFactoryImpl.
 */
public class PreparationResultFactoryImpl extends BaseFactory<PreparationResult> implements PreparationResultFactory {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.preparationresult.PreparationResultFactory#createPreparationResult()
	 */
	@Override
	public PreparationResult createPreparationResult() {
		return new PreparationResult();
	}

}
