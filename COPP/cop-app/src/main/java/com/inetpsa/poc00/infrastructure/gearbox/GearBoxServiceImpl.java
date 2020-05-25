/*
 * Creation : Jan 2, 2017
 */
package com.inetpsa.poc00.infrastructure.gearbox;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.gearbox.GearBoxService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.gearbox.GearBox;
import com.inetpsa.poc00.domain.gearbox.GearBoxRepository;

/**
 * The Class GearBoxServiceImpl.
 */
public class GearBoxServiceImpl implements GearBoxService {

    /** The gear box repo. */
    @Inject
    GearBoxRepository gearBoxRepo;

    /** The trace service. */
    @Inject
    TraceabilityService traceService;

    /** The logger. */
    @Logging
    Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.gearbox.GearBoxService#saveGearBox(com.inetpsa.poc00.domain.gearbox.GearBox)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public GearBox saveGearBox(GearBox gearBox) {
        GearBox gearBoxResponse;
        if (gearBox.getEntityId() == null) {
            // save
            gearBoxResponse = gearBoxRepo.saveGearBox(gearBox);
            traceService.historyForSave(gearBoxResponse, ConstantsApp.COMMONGENOME_SCREEN_ID);
        } else {
            // update
            GearBox oldGearBox = gearBoxRepo.load(gearBox.getEntityId());
            traceService.historyForUpdate(oldGearBox, gearBox, ConstantsApp.COMMONGENOME_SCREEN_ID);
            gearBoxResponse = gearBoxRepo.persistGearBox(gearBox);
        }
        return gearBoxResponse;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.gearbox.GearBoxService#deleteGearBox(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public boolean deleteGearBox(Long gearBoxId) {
        GearBox objToDelete = gearBoxRepo.load(gearBoxId);
        if (objToDelete.getTvvList().isEmpty()) {
            int deletedRow = gearBoxRepo.deleteAll(gearBoxId);
            if (deletedRow > 0) {
                logger.info("Sucessfully deleted GearBox data");
                traceService.historyForDelete(objToDelete, ConstantsApp.COMMONGENOME_SCREEN_ID);
                return true;
            }
            logger.error(" Error occured while deleting data  :", gearBoxId);
            return false;
        }
        logger.warn("Cann't delete GearBox as used in other table : foreign key constraint");
        return false;

    }

}
