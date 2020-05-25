/*
 * Creation : Dec 30, 2016
 */
package com.inetpsa.poc00.infrastructure.bodywork;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.bodywork.BodyWorkService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.bodywork.Bodywork;
import com.inetpsa.poc00.domain.bodywork.BodyworkRepository;

/**
 * The Class BodyWorkServiceImpl.
 */
public class BodyWorkServiceImpl implements BodyWorkService {

    /** The bodywork repo. */
    @Inject
    BodyworkRepository bodyworkRepo;

    /** The logger. */
    @Logging
    private Logger logger;

    @Inject
    TraceabilityService tracService;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.bodywork.BodyWorkService#saveBodyWork(com.inetpsa.poc00.domain.bodywork.Bodywork)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Bodywork saveBodyWork(Bodywork bodyWork) {

        Bodywork bodyWorkRespose;
        if (bodyWork.getEntityId() == null) {
            // save
            bodyWorkRespose = bodyworkRepo.saveBodywork(bodyWork);
            tracService.historyForSave(bodyWorkRespose, ConstantsApp.COMMONGENOME_SCREEN_ID);

        } else {
            // update
            Bodywork oldBodywork = bodyworkRepo.load(bodyWork.getEntityId());
            tracService.historyForUpdate(oldBodywork, bodyWork, ConstantsApp.COMMONGENOME_SCREEN_ID);
            bodyWorkRespose = bodyworkRepo.persistBodywork(bodyWork);

        }
        return bodyWorkRespose;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.bodywork.BodyWorkService#delelteBodyWork(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public boolean delelteBodyWork(Long bodyWorkId) {
        Bodywork objToDelete = bodyworkRepo.load(bodyWorkId);

        if (objToDelete.getTvvList().isEmpty()) {
            int deletedrows = bodyworkRepo.deleteAll(bodyWorkId);
            if (deletedrows > 0) {
                tracService.historyForDelete(objToDelete, ConstantsApp.COMMONGENOME_SCREEN_ID);
                logger.info("Sucessfully deleted BodyWork data");
                return true;
            }
            logger.info("Error while deleting BodyWork data");
            return false;
        }
        logger.warn("Can't delete Bodywork as used in other table : foreign key constraint");
        return false;
    }

}
