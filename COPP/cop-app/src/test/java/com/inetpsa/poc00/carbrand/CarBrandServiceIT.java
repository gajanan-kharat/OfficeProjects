/*
 * Creation : Jan 20, 2017
 */
package com.inetpsa.poc00.carbrand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.carbrand.CarBrandService;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.carbrand.CarBrandFactory;
import com.inetpsa.poc00.domain.carbrand.CarBrandRepository;

@RunWith(SeedITRunner.class)
public class CarBrandServiceIT {

    @Inject
    CarBrandRepository carBrandRepo;
    @Inject
    CarBrandFactory carBrandFactory;
    @Inject
    CarBrandService carbrandService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveCarBrand() {
        CarBrand carbrand = carBrandFactory.createCarBrand();
        CarBrand savedCarBrand = carbrandService.saveCarBrand(carbrand);
        CarBrand loadedCarBrand = carBrandRepo.loadCarBrand(savedCarBrand.getEntityId());
        assertNotNull(loadedCarBrand);
        loadedCarBrand.setBrandLabel("CarBrandLabel");
        carbrandService.saveCarBrand(loadedCarBrand);
        CarBrand persistedCarBrand = carBrandRepo.loadCarBrand(loadedCarBrand.getEntityId());
        assertEquals(persistedCarBrand.getBrandLabel(), loadedCarBrand.getBrandLabel());
    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteCarBrand() {
        CarBrand carbrand = carBrandFactory.createCarBrand();
        CarBrand savedCarBrand = carbrandService.saveCarBrand(carbrand);
        carbrandService.deleteCarBrand(savedCarBrand.getEntityId());
        CarBrand loadedCarBrand = carBrandRepo.loadCarBrand(savedCarBrand.getEntityId());
        assertEquals(loadedCarBrand, null);

    }

}
