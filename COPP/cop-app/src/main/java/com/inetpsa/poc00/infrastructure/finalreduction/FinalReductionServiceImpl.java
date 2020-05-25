/*
 * Creation : Jan 2, 2017
 */
package com.inetpsa.poc00.infrastructure.finalreduction;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.finalreduction.FinalReductionService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioRepository;

/**
 * The Class FinalReductionServiceImpl.
 */
public class FinalReductionServiceImpl implements FinalReductionService {

    /** The frr repo. */
    @Inject
    FinalReductionRatioRepository frrRepo;

    /** The trace service. */
    @Inject
    TraceabilityService traceService;

    /** The logger. */
    @Logging
    Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.finalreduction.FinalReductionService#saveFinalReduction(com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FinalReductionRatio saveFinalReduction(FinalReductionRatio finalReduction) {
        FinalReductionRatio finalReductionResponse;
        if (finalReduction.getEntityId() == null) {

            // save
            finalReductionResponse = frrRepo.saveReductionRatio(finalReduction);
            traceService.historyForSave(finalReductionResponse, ConstantsApp.COMMONGENOME_SCREEN_ID);

        } else {
            // update
            FinalReductionRatio oldFrr = frrRepo.load(finalReduction.getEntityId());
            traceService.historyForUpdate(oldFrr, finalReduction, ConstantsApp.COMMONGENOME_SCREEN_ID);
            finalReductionResponse = frrRepo.persistReductionRatio(finalReduction);
        }
        return finalReductionResponse;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.finalreduction.FinalReductionService#deleteFinalReduction(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public boolean deleteFinalReduction(Long finalRedutionId) {
        FinalReductionRatio objToDelete = frrRepo.load(finalRedutionId);
        if (objToDelete.getTvvList().isEmpty()) {
            int deletedrows = frrRepo.deleteAll(finalRedutionId);
            if (deletedrows > 0) {
                logger.info("Sucessfully deleted FinalReduction data");
                traceService.historyForDelete(objToDelete, ConstantsApp.COMMONGENOME_SCREEN_ID);
                return true;
            }
            logger.error(" Error occured while deleting data  :", finalRedutionId);
            return false;
        }
        logger.warn("Can't delete FinalReduction as used in other table : foreign key constraint");
        return false;
    }

}
