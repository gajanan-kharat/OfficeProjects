/*
 * Creation : Jan 27, 2017
 */
package com.inetpsa.poc00.factorcoefflist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.factorcoefficentlist.FactorCoefficentListService;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListFactory;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;

@RunWith(SeedITRunner.class)
public class FactorCoeffListServiceIT {

    @Inject
    FactorCoeffListRepository factorCoeffListRepo;
    @Inject
    FactorCoeffListFactory factorCoeffListFactory;
    @Inject
    FactorCoefficentListService factorCoeffListService;
    @Inject
    EmissionStandardFactory emissionStandardFactory;
    @Inject
    EmissionStandardRepository emissionStandardRepo;

    @Test
    @WithUser(id = "poch1", password = "poch1")

    public void createEmsDepFCL() {

        EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
        EmissionStandard savedEms = emissionStandardRepo.saveEmissionStandard(emissionStandard);
        FactorCoefficentList factorCoeffList = factorCoeffListFactory.createFactorCoeffList();
        FactorCoefficentList createdFactorCoeffList = factorCoeffListService.createEmsDepFCL(savedEms, factorCoeffList);
        assertNotNull(createdFactorCoeffList.getEntityId());
    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteEmsDepFCL() {
        FactorCoefficentList factorCoeffList = factorCoeffListFactory.createFactorCoeffList();
        FactorCoefficentList savedObj = factorCoeffListRepo.saveFactorCoeffList(factorCoeffList);
        factorCoeffListService.deleteEmsDepFCL(savedObj.getEntityId());
        FactorCoefficentList loadedObj = factorCoeffListRepo.loadFactorCoeffList(savedObj.getEntityId());
        assertEquals(loadedObj, null);

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void copySingleFCL() {
        EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
        EmissionStandard savedEms = emissionStandardRepo.saveEmissionStandard(emissionStandard);
        FactorCoefficentList factorCoeffList = factorCoeffListFactory.createFactorCoeffList();
        factorCoeffList.setLabel("FactorCoeffList");
        factorCoeffList.setDescription("Description");
        factorCoeffList.setVersion("1.0");
        factorCoeffList.setFcApplicationType(new HashSet<FactorCoeffApplicationType>());
        FactorCoefficentList copiedObj = factorCoeffListService.copySingleFCL(factorCoeffList, savedEms);
        assertEquals(copiedObj.getLabel(), factorCoeffList.getLabel());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void updateEmsDepFCL() {
        EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
        EmissionStandard savedEms = emissionStandardRepo.saveEmissionStandard(emissionStandard);
        FactorCoefficentList factorCoeffList = factorCoeffListFactory.createFactorCoeffList();
        factorCoeffList.setLabel("FactorCoeffList");
        factorCoeffList.setDescription("Description");
        factorCoeffList.setVersion("1.0");
        FactorCoefficentList savedObj = factorCoeffListRepo.saveFactorCoeffList(factorCoeffList);
        FactorCoefficentList updatedObj = factorCoeffListService.updateEmsDepFCL(savedObj, savedEms);
        assertTrue(new Double(updatedObj.getVersion()) == new Double(savedObj.getVersion()) + 1);

    }
}
