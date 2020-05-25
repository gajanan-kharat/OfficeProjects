/*
 * Creation : Jan 23, 2017
 */
package com.inetpsa.poc00.fuel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.fuel.FuelService;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelFactory;
import com.inetpsa.poc00.domain.fuel.FuelRepository;

@RunWith(SeedITRunner.class)
public class FuelServiceIT {

    @Inject
    FuelRepository fuelRepo;

    @Inject
    FuelFactory fuelFactory;

    @Inject
    FuelService fuelService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveFuel() {
        Fuel fuel = fuelFactory.createFuel();
        Fuel savedFuel = fuelService.saveFuel(fuel);
        Fuel loadedFuel = fuelRepo.loadFuel(savedFuel.getEntityId());
        assertNotNull(loadedFuel.getEntityId());
        loadedFuel.setLabel("FuelLabel");
        Fuel updatedFuel = fuelService.saveFuel(loadedFuel);
        Fuel loadedUpdatedFuel = fuelRepo.loadFuel(updatedFuel.getEntityId());
        assertEquals(loadedUpdatedFuel.getLabel(), loadedFuel.getLabel());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteFuel() {
        Fuel fuel = fuelFactory.createFuel();
        Fuel savedFuel = fuelService.saveFuel(fuel);
        Fuel loadedFuel = fuelRepo.loadFuel(savedFuel.getEntityId());
        fuelService.deleteFuel(loadedFuel.getEntityId());
        Fuel deleteDFuel = fuelRepo.loadFuel(loadedFuel.getEntityId());
        assertEquals(deleteDFuel, null);

    }

}
