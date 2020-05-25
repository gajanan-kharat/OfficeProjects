package com.inetpsa.poc00.domain.tvvvaluedtc;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of TvvValuedTestCondtn.
 */

public interface TvvValuedTestConditionRepository extends GenericRepository<TvvValuedTestCondition, Long> {

	/**
	 * Saves the GenomeLCDVTvvValuedTestCondtn.
	 * 
	 * @param object the TvvValuedTestCondtn to save
	 * @return the TvvValuedTestCondtn
	 */
	TvvValuedTestCondition saveTvvValuedTestCondtn(TvvValuedTestCondition object);

	/**
	 * Persists the TvvValuedTestCondtn.
	 * 
	 * @param object the TvvValuedTestCondtn to persist
	 */
	void persistTvvValuedTestCondition(TvvValuedTestCondition object);

	/**
	 * Delete all categories
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return TvvValuedTestCondtn count
	 */
	long count();

	List<TvvValuedTestCondition> getAllValuedConditionForEmsDepList(Long entityId);

	List<TvvValuedTestCondition> getAllValuedConditionForList(Long entityId);
}
