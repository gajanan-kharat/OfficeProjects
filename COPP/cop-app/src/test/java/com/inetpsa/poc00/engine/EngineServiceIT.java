/*
 * Creation : Jan 20, 2017
 */
package com.inetpsa.poc00.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.engine.EngineService;
import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.engine.EngineFactory;
import com.inetpsa.poc00.domain.engine.EngineRepository;

@RunWith(SeedITRunner.class)
public class EngineServiceIT {

    @Inject
    EngineRepository engineRepo;

    @Inject
    EngineFactory engineFactory;

    @Inject
    EngineService engineService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void save() {
        Engine engin = engineFactory.createEngine();
        Engine savedEngin = engineService.saveEngine(engin);
        Engine loadedEngine = engineRepo.loadEngine(savedEngin.getEntityId());
        assertNotNull(loadedEngine);
        loadedEngine.setEngineLabel("EnginLabel");
        engineService.saveEngine(loadedEngine);
        Engine loadedPersisted = engineRepo.loadEngine(savedEngin.getEntityId());
        assertNotNull(loadedPersisted.getEngineLabel());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteEngine() {
        Engine engin = engineFactory.createEngine();
        Engine savedEngin = engineService.saveEngine(engin);
        Engine loadedEngine = engineRepo.loadEngine(savedEngin.getEntityId());
        engineService.deleteEngine(loadedEngine.getEntityId());
        Engine deletedEngine = engineRepo.loadEngine(loadedEngine.getEntityId());
        assertEquals(deletedEngine, null);

    }

}
