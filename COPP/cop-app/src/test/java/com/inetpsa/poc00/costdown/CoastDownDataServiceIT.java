/*
 * Creation : Jan 25, 2017
 */
package com.inetpsa.poc00.costdown;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.coastdown.CoastDownDataService;
import com.inetpsa.poc00.domain.coastdown.CoastDownFactory;
import com.inetpsa.poc00.domain.coastdown.CoastDownRepository;
import com.inetpsa.poc00.domain.inertia.InertiaFactory;
import com.inetpsa.poc00.domain.inertia.InertiaRepository;

@RunWith(SeedITRunner.class)
public class CoastDownDataServiceIT {

    @Inject
    CoastDownRepository costDownRepo;
    @Inject
    CoastDownFactory costDownFactory;
    @Inject
    CoastDownDataService costdownDataService;
    @Inject
    InertiaFactory inertiaFactory;
    @Inject
    InertiaRepository inertiaRepo;

    @Test
    @WithUser(id = "pocm1", password = "pocm1")
    public void saveCoastDown() {
        // Inertia inertia = inertiaFactory.createInertia();
        // inertia.setEntityId(1L);
        // inertia.setValue(1);
        // Inertia savedInertia = inertiaRepo.saveInertia(inertia);
        // CoastDown coastDown = costDownFactory.createCoastDown();
        // coastDown.setInertia(savedInertia);
        // coastDown.setpSAReference("pSAReference");
        // coastDown.setVersion("1.0");
        // CoastDown savedCostDown = costDownRepo.saveCoastDown(coastDown);
        // CoastDown coastDownnew = costDownFactory.createCoastDown();
        // costdownDataService.saveCoastDown(coastDown, inertia.getValue(), (long) 0);

    }

}
