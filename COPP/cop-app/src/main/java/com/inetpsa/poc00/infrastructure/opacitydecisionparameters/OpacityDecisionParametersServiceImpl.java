
package com.inetpsa.poc00.infrastructure.opacitydecisionparameters;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.opacitydecisionparameters.OpacityDecisionParametersService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersRepository;

/**
 * The Class OpacityDecisionParametersServiceImpl.
 */
public class OpacityDecisionParametersServiceImpl implements OpacityDecisionParametersService {

	/** The odp repository. */
	@Inject
	OpacityDecisionParametersRepository odpRepository;

	/** The trace service. */
	@Inject
	TraceabilityService traceService;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.CODecisionParametersService.OpacityDecisionParametersService#deleteOpacityDecisionParameters(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public int deleteOpacityDecisionParameters(Long id) {

		int deletedRows = odpRepository.deleteOpacityDecisionParameters(id);
		if (deletedRows > 0) {

			return deletedRows;
		}

		return -1;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.CODecisionParametersService.OpacityDecisionParametersService#saveOpacityDecisionParameters(com.inetpsa.poc00.domain.Opacitydecisionparameters.OpacityDecisionParameters)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public OpacityDecisionParameters saveOpacityDecisionParameters(List<OpacityDecisionParameters> odpDataList, OpacityDecisionParameters updatedODP, Long odpEntityId) {
		OpacityDecisionParameters odpRepresentationResponse;
		if (!odpDataList.isEmpty() && odpEntityId != null) {

			if (odpDataList.get(0).getEntityId().equals(odpEntityId)) {
				// update
				odpRepresentationResponse = updateODP(updatedODP, odpEntityId);

			} else {
				// error
				logger.info("duplicate OpacityDecisionParameters data");

				odpRepresentationResponse = null;

			}
		} else if (odpEntityId == null) {

			// save
			odpRepresentationResponse = odpRepository.saveOpacityDecisionParameters(updatedODP);
			logger.info("******************************************** Saving History for OpacityDecisionParameters, Id : " + updatedODP.getEntityId());
			traceService.historyForSave(updatedODP, ConstantsApp.SPECIFICCOP_SCREEN_ID);

		} else {
			// update

			odpRepresentationResponse = updateODP(updatedODP, odpEntityId);

		}

		return odpRepresentationResponse;
	}

	private OpacityDecisionParameters updateODP(OpacityDecisionParameters updatedODP, Long odpEntityId) {
		OpacityDecisionParameters odpRepresentationResponse;
		OpacityDecisionParameters oldODP = odpRepository.load(odpEntityId);

		traceService.historyForUpdate(oldODP, updatedODP, ConstantsApp.SPECIFICCOP_SCREEN_ID);

		odpRepresentationResponse = odpRepository.saveOpacityDecisionParameters(updatedODP);
		return odpRepresentationResponse;
	}

}
