package com.inetpsa.poc00.domain.factorcoefficient;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of FactorCoefficents.
 */

public interface FactorCoeffRepository extends GenericRepository<FactorCoefficents, Long> {

	/**
	 * Saves the FactorCoefficents.
	 * 
	 * @param object the FactorCoefficents to save
	 * @return the FactorCoefficents
	 */
	FactorCoefficents saveFactorCoefficents(FactorCoefficents object);

	/**
	 * Persists the FactorCoefficents.
	 * 
	 * @param object the FactorCoefficents to persist
	 */
	void persistFactorCoefficents(FactorCoefficents object);

	/**
	 * Delete all categories.
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return FactorCoefficents count
	 */
	long count();

	/**
	 * Deletefactor coefficient.
	 * 
	 * @param entityId the entity id
	 */
	void deletefactorCoefficient(long entityId);

	/**
	 * Gets the all f actors for dep list.
	 * 
	 * @param entityId the entity id
	 * @return the all f actors for dep list
	 */
	List<FactorCoefficents> getAllFActorsForDepList(Long entityId);

}
