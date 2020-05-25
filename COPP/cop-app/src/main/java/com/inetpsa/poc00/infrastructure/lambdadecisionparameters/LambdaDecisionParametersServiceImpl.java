
package com.inetpsa.poc00.infrastructure.lambdadecisionparameters;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.lambdadecisionparameters.LambdaDecisionParametersService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersRepository;

/**
 * The Class LambdaDecisionParametersServiceImpl.
 */
public class LambdaDecisionParametersServiceImpl implements LambdaDecisionParametersService {

	/** The ldp repository. */
	@Inject
	LambdaDecisionParametersRepository ldpRepository;

	/** The trace service. */
	@Inject
	TraceabilityService traceService;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.lambdadecisionparameters.LambdaDecisionParametersService#deleteLambdaDecisionParameters(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public int deleteLambdaDecisionParameters(Long id) {

		int deletedRows = ldpRepository.deleteLambdaDecisionParameters(id);
		if (deletedRows > 0) {

			return deletedRows;
		}

		return -1;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.lambdadecisionparameters.LambdaDecisionParametersService#saveLambdaDecisionParameters(java.util.List,
	 *      com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters, java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public LambdaDecisionParameters saveLambdaDecisionParameters(List<LambdaDecisionParameters> ldpDataList, LambdaDecisionParameters updatedLDP, Long ldpEntityId) {
		LambdaDecisionParameters ldpRepresentationResponse;
		if (!ldpDataList.isEmpty() && ldpEntityId != null) {

			if (ldpDataList.get(0).getEntityId().equals(ldpEntityId)) {
				// update
				ldpRepresentationResponse = updateLDP(updatedLDP, ldpEntityId);

			} else {
				// error
				logger.info("duplicate LambdaDecisionParameters data");

				ldpRepresentationResponse = null;

			}
		} else if (ldpEntityId == null) {

			// save
			ldpRepresentationResponse = ldpRepository.saveLambdaDecisionParameters(updatedLDP);
			logger.info("******************************************** Saving History for LambdaDecisionParameters, Id : " + updatedLDP.getEntityId());
			traceService.historyForSave(updatedLDP, ConstantsApp.SPECIFICCOP_SCREEN_ID);

		} else {
			// update

			ldpRepresentationResponse = updateLDP(updatedLDP, ldpEntityId);

		}

		return ldpRepresentationResponse;
	}

	/**
	 * Update ldp.
	 *
	 * @param updatedLDP the updated ldp
	 * @param ldpEntityId the ldp entity id
	 * @return the lambda decision parameters
	 */
	private LambdaDecisionParameters updateLDP(LambdaDecisionParameters updatedLDP, Long ldpEntityId) {
		LambdaDecisionParameters ldpRepresentationResponse;
		LambdaDecisionParameters oldLDP = ldpRepository.load(ldpEntityId);

		traceService.historyForUpdate(oldLDP, updatedLDP, ConstantsApp.SPECIFICCOP_SCREEN_ID);

		ldpRepresentationResponse = ldpRepository.saveLambdaDecisionParameters(updatedLDP);
		return ldpRepresentationResponse;
	}
}
