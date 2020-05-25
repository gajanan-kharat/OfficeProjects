package com.inetpsa.poc00.domain.preparationfile;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface PreparationFileRepository.
 */
public interface PreparationFileRepository extends GenericRepository<PreparationFile, Long> {

	/**
	 * Save preparation file.
	 * 
	 * @param preparationFile the preparation file
	 * @return the preparation file
	 */
	public PreparationFile savePreparationFile(PreparationFile preparationFile);

}
