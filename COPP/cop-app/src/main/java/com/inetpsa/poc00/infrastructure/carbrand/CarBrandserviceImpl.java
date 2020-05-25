/*
 * Creation : Jan 2, 2017
 */
package com.inetpsa.poc00.infrastructure.carbrand;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.carbrand.CarBrandService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.carbrand.CarBrandRepository;

/**
 * The Class CarBrandserviceImpl.
 */
public class CarBrandserviceImpl implements CarBrandService {

    /** The car brand repo. */
    @Inject
    CarBrandRepository carBrandRepo;

    /** The trace service. */
    @Inject
    TraceabilityService traceService;

    /** The logger. */
    @Logging
    Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.carbrand.CarBrandService#saveCarBrand(com.inetpsa.poc00.domain.carbrand.CarBrand)
     */
    @Override

    public CarBrand saveCarBrand(CarBrand carBrand) {
        CarBrand carBrandResponse;
        if (carBrand.getEntityId() == null) {
            // save
            carBrandResponse = carBrandRepo.saveCarBrand(carBrand);

            traceService.historyForSave(carBrandResponse, ConstantsApp.COMMONGENOME_SCREEN_ID);

        } else {
            // update

            CarBrand oldCarBrand = carBrandRepo.loadCarBrand(carBrand.getEntityId());
            traceService.historyForUpdate(oldCarBrand, carBrand, ConstantsApp.COMMONGENOME_SCREEN_ID);
            carBrandResponse = carBrandRepo.persistCarBrand(carBrand);
        }

        return carBrandResponse;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.carbrand.CarBrandService#deleteCarBrand(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public boolean deleteCarBrand(Long carBrandId) {
        CarBrand objToDelete = carBrandRepo.loadCarBrand(carBrandId);

        if (objToDelete.getTvvSet().isEmpty()) {
            int deletedrows = carBrandRepo.deleteAll(carBrandId);
            if (deletedrows > 0) {
                logger.info("sucessfully deleted CarBrand data");
                traceService.historyForDelete(objToDelete, ConstantsApp.COMMONGENOME_SCREEN_ID);
                return true;
            }
            logger.info("Error while deleting CarBrand data");
            return false;
        }
        logger.warn("Can't delete CarBrand as used in other table : foreign key constraint");
        return false;
    }

}
