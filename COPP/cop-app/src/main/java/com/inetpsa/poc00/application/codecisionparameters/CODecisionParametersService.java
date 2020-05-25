
package com.inetpsa.poc00.application.codecisionparameters;

import java.util.List;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters;

/**
 * The Interface CODecisionParametersService.
 */
@Service
public interface CODecisionParametersService {

	/**
	 * Delete CO decision parameters.
	 *
	 * @param entityId the entity id
	 * @return the int
	 */
	int deleteCODecisionParameters(Long entityId);

	/**
	 * Save co decision parameters.
	 *
	 * @param cdpDataList the cdp data list
	 * @param updatedCDP the updated cdp
	 * @param cdpEntityId the cdp entity id
	 * @return the CO decision parameters
	 */
	CODecisionParameters saveCODecisionParameters(List<CODecisionParameters> cdpDataList, CODecisionParameters updatedCDP, Long cdpEntityId);

}
