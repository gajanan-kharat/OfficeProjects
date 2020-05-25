package com.inetpsa.poc00.domain.opacitydecisionparameters;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface OpacityDecisionParametersRepository.
 */
public interface OpacityDecisionParametersRepository extends GenericRepository<OpacityDecisionParameters, Long> {

	/**
	 * Save Opacity decision parameters.
	 *
	 * @param opacityDecisionParameters the opacity decision parameters
	 * @return the Opacity decision parameters
	 */
	OpacityDecisionParameters saveOpacityDecisionParameters(OpacityDecisionParameters opacityDecisionParameters);

	/**
	 * Persist Opacity decision parameters.
	 *
	 * @param opacityDecisionParameters the opacity decision parameters
	 * @return the Opacity decision parameters
	 */
	OpacityDecisionParameters persistOpacityDecisionParameters(OpacityDecisionParameters opacityDecisionParameters);

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
	 * Gets the all Opacity decision parameters.
	 *
	 * @return the all Opacity decision parameters
	 */
	public List<OpacityDecisionParameters> getAllOpacityDecisionParameters();

	/**
	 * Delete opacity decision parameters.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteOpacityDecisionParameters(Long id);
}
