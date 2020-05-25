package com.inetpsa.poc00.domain.pollutantgaslimit;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of PollutantGasLimit.
 */

public interface PollutantGasLimitRepository extends GenericRepository<PollutantGasLimit, Long> {

	/**
	 * Saves the PollutantGasLimit.
	 * 
	 * @param object the PollutantGasLimit to save
	 * @return the PollutantGasLimit
	 */
	PollutantGasLimit savePollutantGasLimit(PollutantGasLimit object);

	/**
	 * Persists the PollutantGasLimit.
	 * 
	 * @param object the PollutantGasLimit to persist
	 */
	void persistPollutantGasLimit(PollutantGasLimit object);

	/**
	 * Delete all categories
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return PollutantGasLimit count
	 */
	long count();

	void deletePollutantGasLimit(long entityId);

	List<PollutantGasLimit> getAllPollutantsForDepList(Long entityId);
}
