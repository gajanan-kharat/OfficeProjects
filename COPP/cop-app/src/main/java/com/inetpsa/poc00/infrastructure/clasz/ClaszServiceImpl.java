/*
 * Creation : Jan 4, 2017
 */
package com.inetpsa.poc00.infrastructure.clasz;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.application.clasz.ClaszService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszRepository;

/**
 * The Class ClaszServiceImpl.
 */
public class ClaszServiceImpl implements ClaszService {

    /** The clasz repo. */
    @Inject
    ClaszRepository claszRepo;

    /** The trace service. */
    @Inject
    TraceabilityService traceService;

    /** The logger. */
    @Logging
    Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.clasz.ClaszService#saveClasz(com.inetpsa.poc00.domain.clasz.Clasz)
     */
    @Override
    public Clasz saveClasz(Clasz clasz) {
        Clasz claszResponse;
        List<Clasz> claszData = claszRepo.getClaszByLabel(clasz.getLabel());
        if (!claszData.isEmpty()) {

            if (claszData.get(0).getEntityId() == clasz.getEntityId()) {
                // update
                Clasz oldClasz = claszRepo.loadClasz(clasz.getEntityId());
                claszResponse = claszRepo.saveClasz(clasz);

                if (claszResponse != null) {
                    logger.info("History For Updated Clasz Object, Id : " + clasz.getEntityId());
                    traceService.historyForUpdate(oldClasz, clasz, ConstantsApp.SPECIFICCOP_SCREEN_ID);
                }
            } else {
                // error
                return null;
            }
        } else if (clasz.getEntityId() == null) {
            // save
            claszResponse = claszRepo.saveClasz(clasz);

            traceService.historyForSave(clasz, ConstantsApp.SPECIFICCOP_SCREEN_ID);
        } else {
            // update
            Clasz existingClasz = claszRepo.load(clasz.getEntityId());
            traceService.historyForUpdate(existingClasz, clasz, ConstantsApp.SPECIFICCOP_SCREEN_ID);
            claszResponse = claszRepo.saveClasz(clasz);
        }
        return claszResponse;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.clasz.ClaszService#deleteClasz(java.lang.Long)
     */
    @Override
    public boolean deleteClasz(Long claszId) {
        Clasz objToDelete = claszRepo.loadClasz(claszId);

        if (objToDelete.getCategorySet().isEmpty() && objToDelete.getFactorCoefficentList().isEmpty()
                && objToDelete.getPollutantGasLimitList().isEmpty()) {

            int deletedrow = claszRepo.deleteClasz(claszId);

            if (deletedrow > 0) {
                logger.info("History For Deleting Clasz Object, Id : ", claszId);

                traceService.historyForDelete(objToDelete, ConstantsApp.SPECIFICCOP_SCREEN_ID);
                return true;
            }
            logger.error(" Error occured while deleting data  :", claszId);
            return false;

        }
        logger.warn("Can't delete Clasz as used in other table : foreign key constraint");
        return false;
    }

}
