/*
 * Creation : Dec 27, 2016
 */
package com.inetpsa.poc00.infrastructure.tvvdeptdl;

import java.util.ArrayList;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.application.tvvdeptdl.TvvDepTDLService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.generictd.GenericTechDataFactory;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLFactory;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository;

/**
 * The Class TvvDepTDLServiceImpl.
 */
public class TvvDepTDLServiceImpl implements TvvDepTDLService {

	/** The tvv dep tdl repository. */
	@Inject
	private TvvDepTDLRepository tvvDepTDLRepository;

	/** The tvv dep tdl factory. */
	@Inject
	private TvvDepTDLFactory tvvDepTDLFactory;

	/** The generic technical data factory. */
	@Inject
	private GenericTechDataFactory genericTechnicalDataFactory;
	@Inject
	TraceabilityService traceabilityService;
	private static final Logger LOGGER = LoggerFactory.getLogger(TvvDepTDLServiceImpl.class);

	/**
	 * @param tvvDepTDL
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public int deleteTvvDepTDL(TvvDepTDL tvvDepTDL) {

		int response = 0;
		try {
			if (tvvDepTDL.getEntityId() != 0) {

				tvvDepTDLRepository.deleteTvvDepTDL(tvvDepTDL.getEntityId());
				traceabilityService.historyForDelete(tvvDepTDL, ConstantsApp.TVV_DEPENDENT_TDL_SCREEN);
				response = 0;
			}
		} catch (Exception e) {
			LOGGER.error("Error in deleting TvvDepTDL", e);
			response = -1;
		}
		return response;
	}

	/**
	 * @param tvvDepTDL
	 * @return
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TvvDepTDL updateTvvDepTDL(TvvDepTDL tvvDepTDL) {
		TvvDepTDL objectToReturn;
		String[] majoMinorVersion = tvvDepTDL.getVersion().split("\\.");
		long majorVersion = Long.parseLong(majoMinorVersion[0]) + 1;
		long minorVersion = Long.parseLong(majoMinorVersion[1]);
		tvvDepTDL.setVersion(majorVersion + "." + minorVersion);
		TvvDepTDL newOBJ = tvvDepTDLFactory.createTvvDepTDL(tvvDepTDL.getLabel(), tvvDepTDL.getDescription(), tvvDepTDL.getVersion());

		newOBJ.setGenericTechnicalData(new ArrayList<GenericTechnicalData>());
		if (tvvDepTDL.getGenericTechnicalData() != null && !tvvDepTDL.getGenericTechnicalData().isEmpty()) {
			for (GenericTechnicalData data : tvvDepTDL.getGenericTechnicalData()) {

				GenericTechnicalData dataToSave = genericTechnicalDataFactory.createGenericTechData(data.getDataType(), data.getDefaultValue(), data.getLabel(), data.getUnit().getValue());
				dataToSave.setTvvDepTDL(newOBJ);
				newOBJ.getGenericTechnicalData().add(dataToSave);

			}
		}

		objectToReturn = tvvDepTDLRepository.saveTvvDepTDL(newOBJ);
		traceabilityService.historyForSave(objectToReturn, ConstantsApp.TVV_DEPENDENT_TDL_SCREEN);
		return objectToReturn;
	}

	/**
	 * @param tvvDepTDL
	 * @return
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TvvDepTDL insertTvvDepTDL(TvvDepTDL tvvDepTDL) {
		TvvDepTDL objectToReturn;
		tvvDepTDL.setVersion("1.0");
		objectToReturn = tvvDepTDLRepository.saveTvvDepTDL(tvvDepTDL);
		traceabilityService.historyForSave(objectToReturn, ConstantsApp.TVV_DEPENDENT_TDL_SCREEN);
		return objectToReturn;
	}

}
