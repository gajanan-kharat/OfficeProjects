package com.inetpsa.poc00.domain.technicalcase;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of dictionary.
 */

public interface TechCaseRepository extends GenericRepository<TechnicalCase, Long> {

	/**
	 * Saves the TechnicalCase.
	 * 
	 * @param object the TechnicalCase to save
	 * @return the TechnicalCase
	 */
	TechnicalCase saveTechCase(TechnicalCase object);

	/**
	 * Delete all categories.
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return TechnicalCase count
	 */
	long count();

	/**
	 * Load technical case.
	 * 
	 * @param entityId the entity id
	 * @return the technical case
	 */
	public TechnicalCase loadTechnicalCase(long entityId);

    public List<TechnicalCase> getTechnicalCasesForTG(Long technicalGroupId);
}
