/*
 * Creation : Jan 4, 2017
 */
package com.inetpsa.poc00.infrastructure.fuel;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.fuel.FuelService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelRepository;

/**
 * The Class FuelServiceImpl.
 */
public class FuelServiceImpl implements FuelService {

    /** The fuel repo. */
    @Inject
    FuelRepository fuelRepo;

    /** The trace service. */
    @Inject
    TraceabilityService traceService;

    /** The logger. */
    @Logging
    private Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.fuel.FuelService#saveFuel(com.inetpsa.poc00.domain.fuel.Fuel)
     */
    @Override
    public Fuel saveFuel(Fuel fuel) {
        Fuel fuelResponse;
        List<Fuel> fuelDataList = fuelRepo.getFuelByLabel(fuel.getLabel());
        if (fuelDataList.size() > 0) {

            if (fuelDataList.get(0).getEntityId() == fuel.getEntityId()) {

                // update
                Fuel oldFuel = fuelRepo.loadFuel(fuel.getEntityId());
                traceService.historyForUpdate(oldFuel, fuel, ConstantsApp.SPECIFICCOP_SCREEN_ID);
                fuelResponse = fuelRepo.saveFuel(fuel);

            } else {
                // error
                return null;
            }
        } else if (fuel.getEntityId() == null) {
            // save
            fuelResponse = fuelRepo.saveFuel(fuel);
            logger.info("******************************************** Saving History for Fuel, Id : " + fuelResponse.getEntityId());
            traceService.historyForSave(fuel, ConstantsApp.SPECIFICCOP_SCREEN_ID);

        } else {
            // update
            Fuel oldFuel = fuelRepo.loadFuel(fuel.getEntityId());
            traceService.historyForUpdate(oldFuel, fuel, ConstantsApp.SPECIFICCOP_SCREEN_ID);
            fuelResponse = fuelRepo.saveFuel(fuel);

        }
        return fuelResponse;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.fuel.FuelService#deleteFuel(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public boolean deleteFuel(Long fuelId) {
        Fuel objToDelete = fuelRepo.loadFuel(fuelId);
        if (objToDelete.getEmissionStandards().isEmpty() && objToDelete.getFactorCoefficentList().isEmpty()
                && objToDelete.getPollutantGasLimitList().isEmpty() && objToDelete.getTvvList().isEmpty()) {

            int deletedRow = fuelRepo.deleteFuel(fuelId);
            if (deletedRow > 0) {

                logger.info("History For Deleting Clasz Object, Id : " + fuelId);

                traceService.historyForDelete(objToDelete, ConstantsApp.SPECIFICCOP_SCREEN_ID);
                return true;
            }
            logger.error("Error occured while deleting data");
            return false;
        }
        logger.warn("Can't delete Fuel as used in other table : foreign key constraint");
        return false;
    }

}
