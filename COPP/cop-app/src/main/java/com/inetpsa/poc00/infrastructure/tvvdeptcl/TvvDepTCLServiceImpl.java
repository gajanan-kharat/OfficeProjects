/*
 * Creation : Dec 27, 2016
 */
package com.inetpsa.poc00.infrastructure.tvvdeptcl;

import java.util.ArrayList;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.application.tvvdeptcl.TvvDepTCLService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionFactory;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLFactory;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLRepository;

public class TvvDepTCLServiceImpl implements TvvDepTCLService {
	@Inject
	private TvvDepTCLRepository tvvDepTCLRepository;
	@Inject
	private TvvDepTCLFactory tvvDepTCLFactory;
	@Inject
	private GenericTestConditionFactory genericTestConditionFactory;
	@Inject
	private TraceabilityService traceabilityService;
	private static final Logger LOGGER = LoggerFactory.getLogger(TvvDepTCLServiceImpl.class);

	/**
	 * @param tvvDepTCL
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public int deleteTvvDepTCL(TvvDepTCL tvvDepTCL) {

		int response = 0;
		try {
			if (tvvDepTCL.getEntityId() != 0) {

				tvvDepTCLRepository.deleteTvvDepTCL(tvvDepTCL.getEntityId());
				traceabilityService.historyForDelete(tvvDepTCL, ConstantsApp.TVV_DEPENDENT_TCL_SCREEN);
				response = 0;
			}
		} catch (Exception e) {
			LOGGER.error("Error in deleting TvvDepTCL", e);
			response = -1;
		}
		return response;
	}

	/**
	 * @param tvvDepTCL
	 * @return
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TvvDepTCL updateTvvDepTCL(TvvDepTCL tvvDepTCL) {
		TvvDepTCL objectToReturn;
		String[] majoMinorVersion = tvvDepTCL.getVersion().split("\\.");
		long majorVersion = Long.parseLong(majoMinorVersion[0]) + 1;
		long minorVersion = Long.parseLong(majoMinorVersion[1]);
		tvvDepTCL.setVersion(majorVersion + "." + minorVersion);
		TvvDepTCL newOBJ = tvvDepTCLFactory.createTvvDepTCL(tvvDepTCL.getLabel(), tvvDepTCL.getDescription(), tvvDepTCL.getVersion());

		newOBJ.setGenericTestCondition(new ArrayList<GenericTestCondition>());
		if (tvvDepTCL.getGenericTestCondition() != null && !tvvDepTCL.getGenericTestCondition().isEmpty()) {
			for (GenericTestCondition data : tvvDepTCL.getGenericTestCondition()) {

				GenericTestCondition dataToSave = genericTestConditionFactory.createGenericTestCondition(data.getDataType(), data.getDefaultValue(), data.getLabel(), data.getUnit().getValue());
				dataToSave.setTvvDepTCL(newOBJ);
				newOBJ.getGenericTestCondition().add(dataToSave);

			}
		}
		objectToReturn = tvvDepTCLRepository.saveTvvDepTCL(newOBJ);
		traceabilityService.historyForSave(objectToReturn, ConstantsApp.TVV_DEPENDENT_TCL_SCREEN);
		return objectToReturn;
	}

	/**
	 * @param tvvDepTCL
	 * @return
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TvvDepTCL insertTvvDepTCL(TvvDepTCL tvvDepTCL) {
		TvvDepTCL objectToReturn;
		tvvDepTCL.setVersion("1.0");
		objectToReturn = tvvDepTCLRepository.saveTvvDepTCL(tvvDepTCL);
		traceabilityService.historyForSave(objectToReturn, ConstantsApp.TVV_DEPENDENT_TCL_SCREEN);
		return objectToReturn;
	}
}
