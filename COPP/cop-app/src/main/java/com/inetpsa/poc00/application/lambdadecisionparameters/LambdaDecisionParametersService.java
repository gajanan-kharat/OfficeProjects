
package com.inetpsa.poc00.application.lambdadecisionparameters;

import java.util.List;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters;

/**
 * The Interface LambdaDecisionParametersService.
 */
@Service
public interface LambdaDecisionParametersService {

	/**
	 * Delete lambda decision parameters.
	 *
	 * @param entityId the entity id
	 * @return the int
	 */
	int deleteLambdaDecisionParameters(Long entityId);

	/**
	 * Save lambda decision parameters.
	 *
	 * @param ldpDataList the ldp data list
	 * @param updatedLDP the updated ldp
	 * @param ldpEntityId the ldp entity id
	 * @return the lambda decision parameters
	 */
	LambdaDecisionParameters saveLambdaDecisionParameters(List<LambdaDecisionParameters> ldpDataList, LambdaDecisionParameters updatedLDP, Long ldpEntityId);

}
