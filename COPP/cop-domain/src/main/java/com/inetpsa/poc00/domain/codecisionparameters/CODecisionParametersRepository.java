package com.inetpsa.poc00.domain.codecisionparameters;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface CODecisionParametersRepository.
 */
/**
 * 
 * @author ankurk
 *
 */
public interface CODecisionParametersRepository extends GenericRepository<CODecisionParameters, Long> {

	/**
	 * Save CO decision parameters.
	 *
	 * @param coDecisionParameters the CO decision parameters
	 * @return the CO decision parameters
	 */
	CODecisionParameters saveCODecisionParameters(CODecisionParameters coDecisionParameters);

	/**
	 * Persist CO decision parameters.
	 *
	 * @param coDecisionParameters the CO decision parameters
	 * @return the CO decision parameters
	 */
	CODecisionParameters persistCODecisionParameters(CODecisionParameters coDecisionParameters);

	/**
	 * Delete all.
	 *
	 * @return the int
	 */
	int deleteAll();

	/**
	 * Count.
	 *
	 * @return the long
	 */
	long count();

	/**
	 * Gets the all CO decision parameters.
	 *
	 * @return the all CO decision parameters
	 */
	public List<CODecisionParameters> getAllCODecisionParameters();

	/**
	 * Delete co decision parameters.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteCODecisionParameters(Long id);
}
