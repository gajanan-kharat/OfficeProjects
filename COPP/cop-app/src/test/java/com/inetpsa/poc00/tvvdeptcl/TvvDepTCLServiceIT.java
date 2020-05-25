/*
 * Creation : Jan 24, 2017
 */
package com.inetpsa.poc00.tvvdeptcl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.tvvdeptcl.TvvDepTCLService;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLFactory;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLRepository;

@RunWith(SeedITRunner.class)
public class TvvDepTCLServiceIT {

    @Inject
    TvvDepTCLRepository tvvDepTclRepo;

    @Inject
    TvvDepTCLFactory tvvDepTclFactory;

    @Inject
    TvvDepTCLService tvvDepTclService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void insertTvvDepTCL() {
        TvvDepTCL tvvDepTcl = tvvDepTclFactory.createTvvDepTCL();
        TvvDepTCL savedObj = tvvDepTclService.insertTvvDepTCL(tvvDepTcl);
        assertNotNull(savedObj.getEntityId());
    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteTvvDepTCL() {
        TvvDepTCL tvvDepTcl = tvvDepTclFactory.createTvvDepTCL();
        TvvDepTCL savedObj = tvvDepTclService.insertTvvDepTCL(tvvDepTcl);
        int response = tvvDepTclService.deleteTvvDepTCL(savedObj);
        assertEquals(response, 0);
    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void updateTvvDepTCL() {
        TvvDepTCL tvvDepTcl = tvvDepTclFactory.createTvvDepTCL();
        tvvDepTcl.setVersion("1.2");
        tvvDepTcl.setLabel("TvvDepTclLabel");
        TvvDepTCL updatedObj = tvvDepTclService.updateTvvDepTCL(tvvDepTcl);
        TvvDepTCL loadedObj = tvvDepTclRepo.loadTvvDepTcl(updatedObj.getEntityId());
        assertEquals(updatedObj.getLabel(), loadedObj.getLabel());

    }
}
