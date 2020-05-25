
package com.inetpsa.poc00.application.opacitydecisionparameters;

import java.util.List;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters;

/**
 * The Interface OpacityDecisionParametersService.
 */
@Service
public interface OpacityDecisionParametersService {

	/**
	 * Delete opacity decision parameters.
	 *
	 * @param entityId the entity id
	 * @return the int
	 */
	int deleteOpacityDecisionParameters(Long entityId);

	/**
	 * Save opacity decision parameters.
	 *
	 * @param odpDataList the odp data list
	 * @param updatedODP the updated odp
	 * @param odpEntityId the odp entity id
	 * @return the opacity decision parameters
	 */
	OpacityDecisionParameters saveOpacityDecisionParameters(List<OpacityDecisionParameters> odpDataList, OpacityDecisionParameters updatedODP, Long odpEntityId);

}
