package com.inetpsa.poc00.domain.preparationresult;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface PreparationResultRepository.
 */
public interface PreparationResultRepository extends GenericRepository<PreparationResult, Long> {

	/**
	 * Save prep check list.
	 * 
	 * @param preparationResult the preparation result
	 * @return the preparation result
	 */
	public PreparationResult savePrepResultList(PreparationResult preparationResult);

}
