package com.inetpsa.poc00.domain.lambdadecisionparameters;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface LambdaDecisionParametersRepository.
 */
public interface LambdaDecisionParametersRepository extends GenericRepository<LambdaDecisionParameters, Long> {

	/**
	 * Save lambda decision parameters.
	 *
	 * @param lambdaDecisionParameters the lambda decision parameters
	 * @return the lambda decision parameters
	 */
	LambdaDecisionParameters saveLambdaDecisionParameters(LambdaDecisionParameters lambdaDecisionParameters);

	/**
	 * Persist lambda decision parameters.
	 *
	 * @param lambdaDecisionParameters the lambda decision parameters
	 * @return the lambda decision parameters
	 */
	LambdaDecisionParameters persistLambdaDecisionParameters(LambdaDecisionParameters lambdaDecisionParameters);

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
	 * Gets the all lambda decision parameters.
	 *
	 * @return the all lambda decision parameters
	 */
	public List<LambdaDecisionParameters> getAllLambdaDecisionParameters();

	/**
	 * Delete lambda decision parameters.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteLambdaDecisionParameters(Long id);
}
