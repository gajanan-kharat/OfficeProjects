
package com.inetpsa.poc00.infrastructure.codecisionparameters;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.codecisionparameters.CODecisionParametersService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersRepository;

/**
 * The Class CODecisionParametersServiceImpl.
 */
public class CODecisionParametersServiceImpl implements CODecisionParametersService {

	/** The trace service. */
	@Inject
	TraceabilityService traceService;

	/** The logger. */
	@Logging
	private Logger logger;
	/** The cdp repository. */
	@Inject
	CODecisionParametersRepository cdpRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.CODecisionParametersService.CODecisionParametersService#deleteCODecisionParameters(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public int deleteCODecisionParameters(Long id) {

		int deletedRows = cdpRepository.deleteCODecisionParameters(id);
		if (deletedRows > 0) {

			return deletedRows;
		}

		return -1;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.CODecisionParametersService.CODecisionParametersService#saveCODecisionParameters(com.inetpsa.poc00.domain.COdecisionparameters.CODecisionParameters)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public CODecisionParameters saveCODecisionParameters(List<CODecisionParameters> cdpDataList, CODecisionParameters updatedCDP, Long cdpEntityId) {
		CODecisionParameters cdpRepresentationResponse;
		if (!cdpDataList.isEmpty() && cdpEntityId != null) {

			if (cdpDataList.get(0).getEntityId().equals(cdpEntityId)) {
				// update
				cdpRepresentationResponse = updateCDP(updatedCDP, cdpEntityId);

			} else {
				// error
				logger.info("duplicate CODecisionParameters data");

				cdpRepresentationResponse = null;

			}
		} else if (cdpEntityId == null) {

			// save
			cdpRepresentationResponse = cdpRepository.saveCODecisionParameters(updatedCDP);
			logger.info("******************************************** Saving History for CODecisionParameters, Id : " + updatedCDP.getEntityId());
			traceService.historyForSave(updatedCDP, ConstantsApp.SPECIFICCOP_SCREEN_ID);

		} else {
			// update

			cdpRepresentationResponse = updateCDP(updatedCDP, cdpEntityId);

		}

		return cdpRepresentationResponse;
	}

	/**
	 * Update cdp.
	 *
	 * @param updatedCDP the updated cdp
	 * @param cdpEntityId the cdp entity id
	 * @return the lambda decision parameters
	 */
	private CODecisionParameters updateCDP(CODecisionParameters updatedCDP, Long cdpEntityId) {
		CODecisionParameters cdpRepresentationResponse;
		CODecisionParameters oldCDP = cdpRepository.load(cdpEntityId);

		traceService.historyForUpdate(oldCDP, updatedCDP, ConstantsApp.SPECIFICCOP_SCREEN_ID);

		cdpRepresentationResponse = cdpRepository.saveCODecisionParameters(updatedCDP);
		return cdpRepresentationResponse;
	}
}
