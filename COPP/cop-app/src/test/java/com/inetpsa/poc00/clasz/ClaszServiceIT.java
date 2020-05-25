/*
 * Creation : Jan 19, 2017
 */
package com.inetpsa.poc00.clasz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.clasz.ClaszService;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszFactory;
import com.inetpsa.poc00.domain.clasz.ClaszRepository;

@RunWith(SeedITRunner.class)
public class ClaszServiceIT {

    @Inject
    ClaszRepository claszRepo;

    @Inject
    ClaszFactory claszFactory;

    @Inject
    ClaszService claszService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveClasz() {

        Clasz clasz = claszFactory.createClasz();
        clasz.setLabel("NewClasz");
        Clasz savedClasz = claszService.saveClasz(clasz);
        Clasz loadedClasz = claszRepo.loadClasz(savedClasz.getEntityId());
        assertNotNull(loadedClasz.getEntityId());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void delete() {
        Clasz clasz = claszFactory.createClasz();
        clasz.setLabel("NewClasz");
        Clasz savedClasz = claszService.saveClasz(clasz);
        claszRepo.deleteClasz(savedClasz.getEntityId());
        Clasz loadedClasz = claszRepo.loadClasz(savedClasz.getEntityId());
        assertEquals(loadedClasz, null);

    }

}
