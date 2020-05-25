/*
 * Creation : Jan 23, 2017
 */
package com.inetpsa.poc00.gearbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.gearbox.GearBoxService;
import com.inetpsa.poc00.domain.gearbox.GearBox;
import com.inetpsa.poc00.domain.gearbox.GearBoxFactory;
import com.inetpsa.poc00.domain.gearbox.GearBoxRepository;

@RunWith(SeedITRunner.class)
public class GearBoxServiceIT {

    @Inject
    GearBoxRepository gearBoxRepo;

    @Inject
    GearBoxFactory gearBoxFactory;

    @Inject
    GearBoxService gearBoxService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveGearBox() {
        GearBox gearBox = gearBoxFactory.createGearBox();
        GearBox savedGearBox = gearBoxService.saveGearBox(gearBox);
        assertNotNull(savedGearBox.getEntityId());
        savedGearBox.setLabel("GearBox");
        GearBox updatedGearBox = gearBoxService.saveGearBox(savedGearBox);
        GearBox loadedGearBox = gearBoxRepo.loadGearBox(updatedGearBox.getEntityId());
        assertEquals(savedGearBox.getLabel(), loadedGearBox.getLabel());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteGearBox() {
        GearBox gearBox = gearBoxFactory.createGearBox();
        GearBox savedGearBox = gearBoxService.saveGearBox(gearBox);
        gearBoxService.deleteGearBox(savedGearBox.getEntityId());
        GearBox loadedGearBox = gearBoxRepo.loadGearBox(savedGearBox.getEntityId());
        assertEquals(loadedGearBox, null);
    }

}
