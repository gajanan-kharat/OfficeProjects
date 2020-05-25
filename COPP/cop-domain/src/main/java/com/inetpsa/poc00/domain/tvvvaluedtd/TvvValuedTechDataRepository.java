package com.inetpsa.poc00.domain.tvvvaluedtd;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of TvvValuedTechData.
 */

public interface TvvValuedTechDataRepository extends GenericRepository<TvvValuedTechData, Long> {

	/**
	 * Saves the GenomeLCDVTvvValuedTechData.
	 *
	 * @param object the TvvValuedTechData to save
	 * @return the TvvValuedTechData
	 */
	TvvValuedTechData saveTvvValuedTechData(TvvValuedTechData object);

	/**
	 * Persists the TvvValuedTechData.
	 *
	 * @param object the TvvValuedTechData to persist
	 */
	void persistTvvValuedTechData(TvvValuedTechData object);

	/**
	 * Delete all categories
	 *
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 *
	 * @return TvvValuedTechData count
	 */
	long count();

	List<TvvValuedTechData> getAllValuedDataForList(Long entityId);

	List<TvvValuedTechData> getAllValuedDataForEmsDepList(Long entityId);
}
