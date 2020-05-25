package com.inetpsa.poc00.domain.preparationresult;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating PreparationResult objects.
 */
public interface PreparationResultFactory extends GenericFactory<PreparationResult> {

	/**
	 * Creates a new PreparationResult object.
	 * 
	 * @return the preparation result
	 */
	PreparationResult createPreparationResult();
}
