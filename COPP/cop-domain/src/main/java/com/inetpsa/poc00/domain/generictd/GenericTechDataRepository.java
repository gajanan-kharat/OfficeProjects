package com.inetpsa.poc00.domain.generictd;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of dictionary.
 */

public interface GenericTechDataRepository extends GenericRepository<GenericTechnicalData, Long> {

	/**
	 * Saves the GenomeLCDVDictionary.
	 * 
	 * @param dictionary the dictionary to save
	 * @return the dictionary
	 */
	GenericTechnicalData saveGenericTechData(GenericTechnicalData object);

	/**
	 * Persists the dictionary.
	 * 
	 * @param dictionary the dictionary to persist
	 */
	void persistGenericTechData(GenericTechnicalData object);

	/**
	 * Delete all categories
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return dictionary count
	 */
	long count();

	List<GenericTechnicalData> getAllGenericDataForEmsList(Long entityId);
}
