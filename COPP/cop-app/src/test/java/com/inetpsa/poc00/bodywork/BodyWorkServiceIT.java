/*
 * Creation : Jan 23, 2017
 */
package com.inetpsa.poc00.bodywork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.bodywork.BodyWorkService;
import com.inetpsa.poc00.domain.bodywork.Bodywork;
import com.inetpsa.poc00.domain.bodywork.BodyworkFactory;
import com.inetpsa.poc00.domain.bodywork.BodyworkRepository;

@RunWith(SeedITRunner.class)
public class BodyWorkServiceIT {

    @Inject
    BodyworkRepository bodyWorkRepo;

    @Inject
    BodyworkFactory bodyWorkFactory;

    @Inject
    BodyWorkService bodyworkService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveBodyWork() {

        Bodywork bodywork = bodyWorkFactory.createBodywork();
        Bodywork savedBodyWork = bodyworkService.saveBodyWork(bodywork);
        assertNotNull(savedBodyWork.getEntityId());
        savedBodyWork.setLabel("BodyWorkLabel");
        Bodywork updatedBodyWork = bodyworkService.saveBodyWork(savedBodyWork);
        Bodywork loadedBodyWork = bodyWorkRepo.loadBodyWork(savedBodyWork.getEntityId());
        assertEquals(loadedBodyWork.getLabel(), savedBodyWork.getLabel());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void delelteBodyWork() {
        Bodywork bodywork = bodyWorkFactory.createBodywork();
        Bodywork savedBodyWork = bodyworkService.saveBodyWork(bodywork);
        bodyworkService.delelteBodyWork(savedBodyWork.getEntityId());
        Bodywork loadedBodyWork = bodyWorkRepo.loadBodyWork(savedBodyWork.getEntityId());
        assertEquals(loadedBodyWork, null);

    }

}
