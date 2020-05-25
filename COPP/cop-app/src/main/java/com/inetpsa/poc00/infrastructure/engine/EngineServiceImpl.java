/*
 * Creation : Jan 3, 2017
 */
package com.inetpsa.poc00.infrastructure.engine;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.engine.EngineService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.engine.EngineRepository;

/**
 * The Class EngineServiceImpl.
 */
public class EngineServiceImpl implements EngineService {

    /** The engine repo. */
    @Inject
    EngineRepository engineRepo;

    /** The trace service. */
    @Inject
    TraceabilityService traceService;

    /** The logger. */
    @Logging
    Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.engine.EngineService#saveEngine(com.inetpsa.poc00.domain.engine.Engine)
     */
    @Override
    public Engine saveEngine(Engine engine) {
        Engine engineResponse;
        if (engine.getEntityId() == null) {
            engineResponse = engineRepo.saveEngine(engine);
            traceService.historyForSave(engineResponse, ConstantsApp.COMMONGENOME_SCREEN_ID);
        } else {
            Engine oldEngine = engineRepo.loadEngine(engine.getEntityId());
            traceService.historyForUpdate(oldEngine, engine, ConstantsApp.COMMONGENOME_SCREEN_ID);
            engineResponse = engineRepo.persistEngine(engine);
        }
        return engineResponse;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.engine.EngineService#deleteEngine(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public boolean deleteEngine(Long engineId) {
        Engine objToDelete = engineRepo.loadEngine(engineId);

        if (objToDelete.getTvvList().isEmpty()) {
            int deletedRow = engineRepo.deleteAll(engineId);

            if (deletedRow > 0) {
                traceService.historyForDelete(objToDelete, ConstantsApp.COMMONGENOME_SCREEN_ID);
                return true;
            }
            logger.error(" Error occured while deleting data  :", engineId);
            return false;
        }
        logger.warn("Can't delete Engine as used in other table : foreign key constraint");
        return false;
    }

}
