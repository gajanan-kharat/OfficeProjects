/*
 * Creation : Jan 5, 2017
 */
package com.inetpsa.poc00.infrastructure.vehiclecoefficent;

import javax.inject.Inject;


import org.seedstack.seed.Logging;

import org.slf4j.Logger;

import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.application.vehiclecoefficent.VehicleCoeffService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffRepository;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoefficents;

/**
 * The Class VehicleCoeffServiceImpl.
 */
public class VehicleCoeffServiceImpl implements VehicleCoeffService {

	/** The v co repo. */
	@Inject
	VehicleCoeffRepository vCoRepo;

	/** The trace service. */
	@Inject
	TraceabilityService traceService;

	/** The logger. */
	@Logging
	Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.vehiclecoefficent.VehicleCoeffService#saveVehicleCoefficents(com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoefficents)
	 */
	@Override
	
	public VehicleCoefficents saveVehicleCoefficents(VehicleCoefficents vehicleCoeff) {
		Long entityid = vehicleCoeff.getEntityId();
		if (entityid != null) {
			VehicleCoefficents oldvCo = vCoRepo.load(entityid);
			logger.info("********************************************History for VehicleCoefficents Update");
			traceService.historyForUpdate(oldvCo, vehicleCoeff, ConstantsApp.SPECIFICCOP_SCREEN_ID);
		} else {
			logger.info("******************************************** Saving History for VehicleCoefficents");
			traceService.historyForSave(vehicleCoeff, ConstantsApp.SPECIFICCOP_SCREEN_ID);
		}
		return vCoRepo.saveVehicleCoefficents(vehicleCoeff);
	}

}
