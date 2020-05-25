/*
 * Creation : Oct 25, 2016
 */
package com.inetpsa.poc00.basket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.application.basket.BasketService;
import com.inetpsa.poc00.domain.basket.Basket;
import com.inetpsa.poc00.domain.basket.BasketRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;

/**
 * The Class BasketIT.
 */
@RunWith(SeedITRunner.class)
public class BasketIT {

    /** The basket service. */
    @Inject
    private BasketService basketService;

    /** The basket repo. */
    @Inject
    private BasketRepository basketRepo;

    /** The vehicle file repo. */
    @Inject
    private VehicleFileRepository vehicleFileRepo;

    /**
     * Delete basket test.
     */
    @Test
    public void deleteBasketTest() {

        Date date = Calendar.getInstance().getTime();
        Basket basketObj = new Basket();

        basketObj.setCreationDate(date);

        Basket newbasketObj = basketRepo.saveBasket(basketObj);

        assertNotNull(newbasketObj);

        VehicleFile vehicleFileObj = new VehicleFile();

        vehicleFileObj.setStatisticalDecision("testDecision");
        vehicleFileObj.setBasket(newbasketObj);

        VehicleFile newVehicleFile = vehicleFileRepo.saveVehicle(vehicleFileObj);

        assertNotNull(newVehicleFile);
        basketService.deleteBasket(newVehicleFile.getEntityId());

        VehicleFile vehicleFileDeleted = vehicleFileRepo.loadVehicle(newVehicleFile.getEntityId());

        assertEquals(vehicleFileDeleted.getBasket(), null);
        // logger.info("**********DELETE tested successfully*********");
    }
}
