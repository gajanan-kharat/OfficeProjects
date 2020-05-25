/*
 * Creation : Jan 3, 2017
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.pollgaslabel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.pollgaslabel.PollGasLabelService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository;

/**
 * The PollGasLabelServiceImpl Class.
 *
 * @author mehaj
 */
public class PollGasLabelServiceImpl implements PollGasLabelService {

	/** The poll gas label repository. */
	@Inject
	PollutantGasLabelRepository pollGasLabelRepository;

	/** The traceability service. */
	@Inject
	TraceabilityService traceabilityService;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(PollGasLabelServiceImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.pollgaslabel.PollGasLabelService#savePollGasLabel(com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public String savePollGasLabel(PollutantGasLabel pollutantGasLabelObj) {

		List<PollutantGasLabel> pollutantGasLabelData = pollGasLabelRepository.getPollutantByLabel(pollutantGasLabelObj.getLabel());
		if (!pollutantGasLabelData.isEmpty()) {

			if (pollutantGasLabelData.get(0).getEntityId() != pollutantGasLabelObj.getEntityId()) {

				return pollutantGasLabelData.get(0).getLabel();

			}
		} else if (pollutantGasLabelObj.getEntityId() == null) {
			if (pollutantGasLabelObj.getLabel() != null && pollutantGasLabelObj.getLabel() != "") {
				// save

				PollutantGasLabel newPollutantGasLabel = pollGasLabelRepository.savePollutantGasLabel(pollutantGasLabelObj);

				traceabilityService.historyForSave(newPollutantGasLabel, ConstantsApp.SPECIFICCOP_SCREEN_ID);
			}
		} else {
			// update
			PollutantGasLabel oldPollutantGasLabel = pollGasLabelRepository.load(pollutantGasLabelObj.getEntityId());
			traceabilityService.historyForUpdate(oldPollutantGasLabel, pollutantGasLabelObj, ConstantsApp.SPECIFICCOP_SCREEN_ID);
			pollGasLabelRepository.persistPollutantGasLabel(pollutantGasLabelObj);

		}

		return ConstantsApp.SUCCESS;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.pollgaslabel.PollGasLabelService#deletePollGasLabel(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public String deletePollGasLabel(Long pollutantGasLabelId) {
		PollutantGasLabel objToDelete = pollGasLabelRepository.load(pollutantGasLabelId);
		try {
			if (objToDelete.getFactorOrCoeffiecients().isEmpty() && objToDelete.getPollutantGasLimits().isEmpty() && objToDelete.getTvvValuedFactorCoefficents().isEmpty()) {
				int deletedRow = pollGasLabelRepository.deletePollutantGasLabel(pollutantGasLabelId);
				if (deletedRow > 0) {
					traceabilityService.historyForDelete(objToDelete, ConstantsApp.SPECIFICCOP_SCREEN_ID);
					return ConstantsApp.SUCCESS;
				}
				logger.error(" Error occured while deleting data  :", pollutantGasLabelId);
				return ConstantsApp.FAILURE;
			}
			logger.warn("Can't delete Pllutant as used in other table : foreign key constraint");
			return ConstantsApp.FAILURE;
		} catch (Exception deletePollGasLabelException) {
			logger.error("deletion exception ,{}", deletePollGasLabelException);
			return ConstantsApp.FAILURE;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.pollgaslabel.PollGasLabelService#getAllUsedPgLabels(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<PollutantGasLabel> getAllUsedPgLabels(Long emissionStdId) {

		List<PollutantGasLabel> pgLabelSet = pollGasLabelRepository.getAllUsedPgLabels(emissionStdId);
		if (pgLabelSet != null) {

			return pgLabelSet;
		}

		return Collections.emptyList();

	}
}
