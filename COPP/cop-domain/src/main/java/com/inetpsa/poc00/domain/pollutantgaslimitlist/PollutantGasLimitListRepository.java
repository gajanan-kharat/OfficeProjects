package com.inetpsa.poc00.domain.pollutantgaslimitlist;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of PollutantGasLimitList.
 */

public interface PollutantGasLimitListRepository extends GenericRepository<PollutantGasLimitList, Long> {

	/**
	 * Saves the PollutantGasLimitList.
	 * 
	 * @param object the PollutantGasLimitList to save
	 * @return the PollutantGasLimitList
	 */
	PollutantGasLimitList savePollutantGasLimitList(PollutantGasLimitList object);

	/**
	 * Persists the PollutantGasLimitList.
	 * 
	 * @param object the PollutantGasLimitList to persist
	 */
	void persistPollutantGasLimitList(PollutantGasLimitList object);

	/**
	 * Delete all categories.
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return PollutantGasLimitList count
	 */
	long count();

	/**
	 * Delete pollutant gas list.
	 * 
	 * @param entityId the entity id
	 */
	void deletePollutantGasList(long entityId);

	/**
	 * Gets the latest emission standard dep lists.
	 * 
	 * @param emsId the ems id
	 * @return the latest emission standard dep lists
	 */
	List<PollutantGasLimitList> getLatestEmissionStandardDepLists(Long emsId);

	/**
	 * Gets the emission standard dep lists.
	 * 
	 * @param esEntityId the es entity id
	 * @return the emission standard dep lists
	 */
	List<PollutantGasLimitList> getEmissionStandardDepLists(Long esEntityId);

	/**
	 * Gets the max version for label.
	 * 
	 * @param label the label
	 * @return the max version for label
	 */
	Double getMaxVersionForLabel(String label);
}
