/*
 * Creation : Jan 10, 2017
 */
package com.inetpsa.poc00.tvv;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.tvv.TvvService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.category.CategoryFactory;
import com.inetpsa.poc00.domain.clasz.ClaszFactory;
import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.engine.EngineFactory;
import com.inetpsa.poc00.domain.engine.EngineRepository;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.factory.CarFactoryObjectCreation;
import com.inetpsa.poc00.domain.factory.CarFactoryRepository;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeFactory;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyFactory;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseFactory;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVFactory;
import com.inetpsa.poc00.domain.tvv.TVVRepository;
import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDownFactory;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechFactory;

@RunWith(SeedITRunner.class)
public class TvvServiceIT {

    @Inject
    TVVFactory tvvFactory;

    @Inject
    TvvService tvvService;

    @Inject
    EmissionStandardFactory emmissionFactory;

    @Inject
    TechCaseFactory technicalCaseFactory;

    @Inject
    EmissionStandardRepository emissionRepository;

    @Inject
    TechCaseRepository technicalCaseRepository;

    @Inject
    TVVRepository tvvRepository;

    @Inject
    TvvValuedCoastDownFactory tvvValuedCostDownFactory;

    @Inject
    StatusFactory statusFactrory;

    @Inject
    StatusRepository statusRepository;

    @Inject
    ProjectCodeFamilyFactory projectCodeFactory;

    @Inject
    CategoryFactory categoryFactory;
    @Inject
    CarFactoryObjectCreation carFactoryObjectCreation;
    @Inject
    ClaszFactory claszFactory;

    @Inject
    VehicleTechFactory vehicleFactory;

    @Inject
    FuelInjctnTypeFactory fuelInjectionFactory;

    @Inject
    EngineFactory enginFactory;

    @Inject
    EngineRepository enginRepository;
    @Inject
    CarFactoryRepository carFactoryRepository;

    @Test

    public void updateTvv() {
        TVV tvv = tvvFactory.createTVV();
        Engine oldEng = enginFactory.createEngine();
        Engine savedEngin = enginRepository.saveEngine(oldEng);
        tvv.setLabel("OldTvv");
        tvv.setEngine(savedEngin);
        tvv.setFactorySet(null);
        EmissionStandard emission = emmissionFactory.createEmissonStandard();
        emission.setVersion("1.0");
        emission.setEsLabel("OldEmission");
        EmissionStandard oldems = emissionRepository.saveEmissionStandard(emission);
        TechnicalCase technicalCase = technicalCaseFactory.createTechCase();
        technicalCase.setEmissionStandard(oldems);
        technicalCase.setTvv(tvv);
        tvv.setTechnicalCase(technicalCase);
        tvv.setVersion("1.0");
        TVV oldTVV = tvvRepository.saveTVV(tvv);
        TVV tvvobj = tvvFactory.createTVV();
        tvvobj.setLabel("NewTvv");
        Engine newsavedEngin = enginRepository.saveEngine(oldEng);
        tvvobj.setEngine(newsavedEngin);
        tvvobj.setFactorySet(null);
        EmissionStandard emission1 = emmissionFactory.createEmissonStandard();
        emission1.setVersion("2.0");
        emission1.setEsLabel("NewEmission");
        EmissionStandard newems = emissionRepository.saveEmissionStandard(emission1);
        TechnicalCase technicalCasenew = technicalCaseFactory.createTechCase();
        technicalCasenew.setEmissionStandard(newems);
        technicalCasenew.setTvv(tvvobj);
        tvvobj.setTechnicalCase(technicalCasenew);
        tvvobj.setVersion("2.0");
        TVV newTvv = tvvRepository.saveTVV(tvvobj);
        tvvService.updateTvv(oldTVV.getEntityId(), newTvv, null);
        TVV loadedTvv = tvvRepository.loadTVV(newTvv.getEntityId());
        assertFalse(oldTVV.getLabel() == loadedTvv.getLabel());
    }

    @Test

    @WithUser(id = "poch1", password = "poch1")
    public void saveTvvObject() {
        boolean changeVersion = true;
        TVV tvv = tvvFactory.createTVV();
        TVV tvvOldObj = tvvRepository.saveTVV(tvv);
        TVV newObj = tvvFactory.createTVV();
        TVV savedObj = tvvService.saveTvvObject(changeVersion, tvvOldObj, newObj);
        assertNotNull(savedObj.getEntityId());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteTvv() {
        TVV tvv = tvvFactory.createTVV();
        Status status = statusFactrory.createStatus(ConstantsApp.DRAFT);
        Status savedStatus = statusRepository.saveStatus(status);
        tvv.setStatus(savedStatus);
        TVV tvvObj = tvvRepository.saveTVV(tvv);
        assertNotNull(tvvObj.getEntityId());
        tvvService.deleteTvv(tvvObj.getEntityId());
        TVV loadedTvv = tvvRepository.loadTVV(tvvObj.getEntityId());
        // assertEquals(loadedTvv, null);

    }

    @Test
    public void copyTvv() {
        TVV tvv = tvvFactory.createTVV();
        EmissionStandard emission = emmissionFactory.createEmissonStandard();
        emission.setVersion("1.0");
        emission.setEsLabel("OldEmission");
        EmissionStandard oldems = emissionRepository.saveEmissionStandard(emission);
        TechnicalCase technicalCase = technicalCaseFactory.createTechCase();
        technicalCase.setEmissionStandard(oldems);
        tvv.setTechnicalCase(technicalCase);
        TVV savedTvv = tvvRepository.saveTVV(tvv);
        TVV copiedTvv = tvvService.copyTvv(savedTvv.getLabel(), savedTvv, null, null, null);
        assertNotNull(copiedTvv.getEntityId());

    }

    /*
     * @Test public void createVersion() { TVV tvv = tvvRepository.loadTVV(4); System.out.println("Loded tvv"); }
     */

}
