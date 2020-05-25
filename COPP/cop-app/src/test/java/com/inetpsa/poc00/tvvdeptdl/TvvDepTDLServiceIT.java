/*
 * Creation : Jan 24, 2017
 */
package com.inetpsa.poc00.tvvdeptdl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.tvvdeptdl.TvvDepTDLService;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLFactory;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository;

@RunWith(SeedITRunner.class)
public class TvvDepTDLServiceIT {

    @Inject

    TvvDepTDLRepository tvvDepTdlRepo;

    @Inject

    TvvDepTDLFactory tvvDepTdlFactory;

    @Inject

    TvvDepTDLService tvvDepTdlService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void insertTvvDepTDL() {
        TvvDepTDL tvvDepTdl = tvvDepTdlFactory.createTvvDepTDL();
        TvvDepTDL savedTvvDepTdl = tvvDepTdlService.insertTvvDepTDL(tvvDepTdl);
        assertNotNull(savedTvvDepTdl.getEntityId());
    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteTvvDepTDL() {
        TvvDepTDL tvvDepTdl = tvvDepTdlFactory.createTvvDepTDL();
        TvvDepTDL savedTvvDepTdl = tvvDepTdlRepo.saveTvvDepTDL(tvvDepTdl);
        int response = tvvDepTdlService.deleteTvvDepTDL(savedTvvDepTdl);
        assertEquals(response, 0);

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void updateTvvDepTDL() {
        TvvDepTDL tvvDepTdl = tvvDepTdlFactory.createTvvDepTDL();
        tvvDepTdl.setLabel("TvvDepTdl");
        tvvDepTdl.setVersion("1.2");
        TvvDepTDL updatedObj = tvvDepTdlService.updateTvvDepTDL(tvvDepTdl);
        TvvDepTDL loadedObj = tvvDepTdlRepo.loadTvvDepTDL(updatedObj.getEntityId());
        assertEquals(updatedObj.getLabel(), loadedObj.getLabel());
    }

}
