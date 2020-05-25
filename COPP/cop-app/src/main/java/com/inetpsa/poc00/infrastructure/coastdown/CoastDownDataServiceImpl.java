/*
 * Creation : Jan 3, 2017
 */
package com.inetpsa.poc00.infrastructure.coastdown;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.application.coastdown.CoastDownDataService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.coastdown.CoastDown;
import com.inetpsa.poc00.domain.coastdown.CoastDownRepository;
import com.inetpsa.poc00.domain.inertia.Inertia;
import com.inetpsa.poc00.domain.inertia.InertiaFactory;
import com.inetpsa.poc00.domain.inertia.InertiaRepository;

/**
 * The Class CoastDownDataServiceImpl.
 */
public class CoastDownDataServiceImpl implements CoastDownDataService {

	/** The inertia factory. */
	@Inject
	InertiaFactory inertiaFactory;

	/** The inertia repo. */
	@Inject
	InertiaRepository inertiaRepo;

	/** The coast down repo. */
	@Inject
	CoastDownRepository coastDownRepo;

	/** The trace service. */
	@Inject
	TraceabilityService traceService;

	/** The logger. */
	@Logging
	Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.coastdown.CoastDownDataService#saveCoastDown(com.inetpsa.poc00.domain.coastdown.CoastDown,
	 *      int, java.lang.Long)
	 */
	@Override
	public CoastDown saveCoastDown(CoastDown coastDown, int inertiaValue, Long coastDownId) {
		CoastDown coastDownResponse;
		if (coastDownId == 0) {
			CoastDown maxVersionObject = coastDownRepo.getMaxVersionForcoastdown(coastDown.getpSAReference(), inertiaValue);

			String newversion = coastDown.getVersion();
			Double newVersionDouble = Double.parseDouble(newversion.substring(1));
			boolean checkVersionFlag = false;
			boolean updateLatestVersionFlag = false;

			if (maxVersionObject.getVersion() != null) {
				String maxversion = maxVersionObject.getVersion();
				Double maxVersionDouble = Double.parseDouble(maxversion.substring(1));
				if (newVersionDouble > maxVersionDouble) {
					checkVersionFlag = true;
					updateLatestVersionFlag = true;

				}
			} else {
				coastDown.setVersion("V1.0");
				checkVersionFlag = true;
			}
			if (checkVersionFlag) {

				List<Inertia> inertiaObj = inertiaRepo.getinertiaObj(inertiaValue);
				if (!inertiaObj.isEmpty()) {
					coastDown.setInertia(inertiaObj.get(0));
					if (updateLatestVersionFlag) {
						coastDownRepo.persistCoastDown(coastDown.getpSAReference(), inertiaObj.get(0).getEntityId(), maxVersionObject.getVersion());
					}
				} else {
					Inertia toSave = inertiaFactory.createInertia();

					toSave.setValue(inertiaValue);
					inertiaRepo.saveInertia(toSave);

					List<Inertia> inertiaObj2 = inertiaRepo.getinertiaObj(inertiaValue);

					if (!inertiaObj2.isEmpty()) {
						coastDown.setInertia(inertiaObj2.get(0));
					}
				}

				coastDownResponse = coastDownRepo.saveCoastDown(coastDown);

				if (coastDownResponse != null) {
					logger.info("******************************************** Saving History for CoastDown, Id : " + coastDownResponse.getEntityId());
					traceService.historyForSave(coastDownResponse, ConstantsApp.COASTDOWN_SCREEN_ID);
				}
			} else {

				return null;

			}
		} else {
			CoastDown oldCoastdown = coastDownRepo.load(coastDownId);
			coastDown.setEntityId(coastDownId);
			List<Inertia> inertiaObj = inertiaRepo.getinertiaObj(inertiaValue);
			coastDown.setInertia(inertiaObj.get(0));
			coastDownResponse = coastDownRepo.saveCoastDown(coastDown);
			if (coastDownResponse != null) {
				logger.info("******************************************** Updating History for CoastDown, Id : " + coastDownResponse.getEntityId());

				traceService.historyForUpdate(oldCoastdown, coastDownResponse, ConstantsApp.COASTDOWN_SCREEN_ID);
			}

		}
		return coastDownResponse;
	}

}
