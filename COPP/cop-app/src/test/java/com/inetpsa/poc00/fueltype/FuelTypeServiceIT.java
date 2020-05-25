/*
 * Creation : Jan 23, 2017
 */
package com.inetpsa.poc00.fueltype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.fueltype.FuelTypeService;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;

@RunWith(SeedITRunner.class)
public class FuelTypeServiceIT {
    @Inject
    FuelTypeRepository fuelTypeRepo;
    @Inject
    FuelTypeFactory fuelTypeFactory;
    @Inject
    FuelTypeService fuelTypeService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveFuelType() {
        FuelType fuelType = fuelTypeFactory.createFuelType();
        FuelType savedFuelType = fuelTypeService.saveFuelType(fuelType);
        assertNotNull(savedFuelType.getEntityId());
        savedFuelType.setFuelTypeLable("FuelTypeLabel");
        FuelType savedupdatedFuelType = fuelTypeService.saveFuelType(savedFuelType);
        FuelType loadedFuelType = fuelTypeRepo.loadFuelType(savedupdatedFuelType.getEntityId());
        assertEquals(loadedFuelType.getFuelTypeLable(), savedFuelType.getFuelTypeLable());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")

    public void deleteFuelType() {
        FuelType fuelType = fuelTypeFactory.createFuelType();
        FuelType savedFuelType = fuelTypeService.saveFuelType(fuelType);
        fuelTypeService.deleteFuelType(savedFuelType.getEntityId());
        FuelType loadedFuelType = fuelTypeRepo.loadFuelType(savedFuelType.getEntityId());
        assertEquals(loadedFuelType, null);

    }

}
