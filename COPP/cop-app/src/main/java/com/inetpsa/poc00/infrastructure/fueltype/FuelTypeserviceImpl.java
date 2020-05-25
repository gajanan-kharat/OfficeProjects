/*
 * Creation : Dec 29, 2016
 */
package com.inetpsa.poc00.infrastructure.fueltype;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.fueltype.FuelTypeService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;

public class FuelTypeserviceImpl implements FuelTypeService {

    /** The logger. */
    @Logging
    private Logger logger;

    @Inject
    private FuelTypeRepository fuelTypeRepo;

    @Inject
    private FuelTypeFactory fuelTypeFactory;

    @Inject
    TraceabilityService traceService;

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FuelType saveFuelType(FuelType fuelType) {
        FuelType fuelTypeResponse = fuelTypeFactory.createFuelType();
        if (fuelType.getEntityId() == null) {
            // save
            fuelTypeResponse = fuelTypeRepo.saveFuelType(fuelType);
            traceService.historyForSave(fuelTypeResponse, ConstantsApp.COMMONGENOME_SCREEN_ID);
        } else {
            // update

            FuelType oldFuelType = fuelTypeRepo.load(fuelType.getEntityId());
            traceService.historyForUpdate(oldFuelType, fuelType, ConstantsApp.COMMONGENOME_SCREEN_ID);
            fuelTypeResponse = fuelTypeRepo.persistFuelType(fuelType);
        }
        return fuelTypeResponse;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public boolean deleteFuelType(Long fuelTypeId) {

        FuelType objToDelete = fuelTypeRepo.load(fuelTypeId);

        if (objToDelete.getFuel().isEmpty() && objToDelete.getEngine().isEmpty()) {
            int deletedrows = fuelTypeRepo.deleteAll(fuelTypeId);

            if (deletedrows > 0) {
                logger.info("sucessfully deleted data");
                traceService.historyForDelete(objToDelete, ConstantsApp.COMMONGENOME_SCREEN_ID);
                return true;
            }
            logger.error(" Error occured while deleting data  :", fuelTypeId);
            return false;
        }
        logger.warn("Can't delete FuelType as used in other table : foreign key constraint");
        return false;

    }

}
