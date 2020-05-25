/*
 * Creation : Jan 24, 2017
 */
package com.inetpsa.poc00.factorcoeffapptypa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.factorcoeffapptype.FactorCoeffAppTypeService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppFactory;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppRepository;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;

@RunWith(SeedITRunner.class)
public class factorcoeffServiceIT {

    @Inject
    FactorCoeffAppRepository factorCoeffAppRepo;

    @Inject
    FactorCoeffAppFactory factorCoeffAppFactory;

    @Inject
    FactorCoeffAppTypeService factorCoeffAppService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveFactorCoeffApplicationType() {
        FactorCoeffApplicationType factorCoeffFactoryApp = factorCoeffAppFactory.createFactorCoefficents();
        factorCoeffFactoryApp.setLabel("NewFactorCoeff");
        String response = factorCoeffAppService.saveFactorCoeffApplicationType(factorCoeffFactoryApp);
        assertEquals(response, ConstantsApp.SUCCESS);
        factorCoeffFactoryApp.setLabel("UpdatedLabel");
        response = factorCoeffAppService.saveFactorCoeffApplicationType(factorCoeffFactoryApp);
        assertEquals(response, ConstantsApp.SUCCESS);
        FactorCoeffApplicationType factorCoeffFactoryAppNew = factorCoeffAppFactory.createFactorCoefficents();
        factorCoeffFactoryAppNew.setLabel("UpdatedLabel");
        response = factorCoeffAppService.saveFactorCoeffApplicationType(factorCoeffFactoryAppNew);
        assertEquals(response, factorCoeffFactoryAppNew.getLabel());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteFactorCoeffApplicationType() {
        FactorCoeffApplicationType factorCoeffFactoryApp = factorCoeffAppFactory.createFactorCoefficents();
        factorCoeffFactoryApp.setLabel("NewFactorCoeff");
        factorCoeffAppService.saveFactorCoeffApplicationType(factorCoeffFactoryApp);
        List<FactorCoeffApplicationType> factorCoeffAppList = factorCoeffAppRepo.getFactorCoeffAppTypeDataByLabel(factorCoeffFactoryApp.getLabel());
        String response = factorCoeffAppService.deleteFactorCoeffApplicationType(factorCoeffAppList.get(0).getEntityId());
        assertEquals(response, ConstantsApp.SUCCESS);
        FactorCoeffApplicationType loadedObj = factorCoeffAppRepo.loadFactorCoeffAppType(factorCoeffAppList.get(0).getEntityId());
        assertEquals(loadedObj, null);

    }

}
