package com.inetpsa.poc00.domain.generictc;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of GenericTestCondition.
 */

public interface GenericTestConditionRepository extends GenericRepository<GenericTestCondition, Long> {

	/**
	 * Saves the GenericTestCondition.
	 * 
	 * @param object the GenericTestCondition to save
	 * @return the GenericTestCondition
	 */
	GenericTestCondition saveGenericTestCondition(GenericTestCondition object);

	/**
	 * Persists the GenericTestCondition.
	 * 
	 * @param object the GenericTestCondition to persist
	 */
	void persistGenericTestCondition(GenericTestCondition object);

	/**
	 * Delete all categories
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return GenericTestCondition count
	 */
	long count();

	List<GenericTestCondition> getAllGenericConditionsForEmsList(Long entityId);
}
