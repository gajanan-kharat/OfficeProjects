/*
 * Creation : Jan 23, 2017
 */
package com.inetpsa.poc00.carfactory;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.carfactory.CarFactoryService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.factory.CarFactoryObjectCreation;
import com.inetpsa.poc00.domain.factory.CarFactoryRepository;

@RunWith(SeedITRunner.class)
public class CarFactoryServiceIT {

    @Inject
    CarFactoryRepository carFactoryRepo;
    @Inject
    CarFactoryObjectCreation carFactoryObjCreation;
    @Inject
    CarFactoryService carFactoryService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveFactoryObject() {
        CarFactory carFactory = carFactoryObjCreation.createCarFactoryObject();
        String response = carFactoryService.saveFactoryObject(carFactory);
        assertEquals(response, ConstantsApp.SUCCESS);
        carFactory.setLabel("UpdatedObject");
        response = carFactoryService.saveFactoryObject(carFactory);
        assertEquals(response, ConstantsApp.SUCCESS);
        CarFactory carFactoryNew = carFactoryObjCreation.createCarFactoryObject();
        carFactoryNew.setLabel("UpdatedObject");
        response = carFactoryService.saveFactoryObject(carFactoryNew);
        assertEquals(carFactoryNew.getLabel(), carFactory.getLabel());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")

    public void deleteCarFactory() {
        CarFactory carFactory = carFactoryObjCreation.createCarFactoryObject();
        carFactory.setLabel("CarFactoryLabel");
        carFactoryService.saveFactoryObject(carFactory);
        List<CarFactory> carFactoryList = carFactoryRepo.getCarFactoryDataByLabel(carFactory.getLabel());
        carFactoryService.deleteCarFactory(carFactoryList.get(0).getEntityId());
        CarFactory loadedCarFactory = carFactoryRepo.loadCarFactory(carFactoryList.get(0).getEntityId());
        assertEquals(loadedCarFactory, null);

    }

}
