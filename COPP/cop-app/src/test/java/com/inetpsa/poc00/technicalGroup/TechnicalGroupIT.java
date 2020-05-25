/*
 * Creation : Dec 16, 2016
 */
package com.inetpsa.poc00.technicalGroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.application.technicalgroup.TechnicalGroupService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupFactory;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseFactory;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupFactory;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;

/**
 * The Class TechnicalGroupIT.
 */
@RunWith(SeedITRunner.class)
public class TechnicalGroupIT {

    /** The technical group factory. */
    @Inject
    TechGroupFactory technicalGroupFactory;
    
    /** The tech group repository. */
    @Inject
    TechGroupRepository techGroupRepository;
    
    /** The technical group service. */
    @Inject
    TechnicalGroupService technicalGroupService;
    
    /** The technical case factory. */
    @Inject
    TechCaseFactory technicalCaseFactory;
    
    /** The technical case repository. */
    @Inject
    TechCaseRepository technicalCaseRepository;
    
    /** The status factrory. */
    @Inject
    StatusFactory statusFactrory;
    
    /** The status repository. */
    @Inject
    StatusRepository statusRepository;
    
    /** The regulation group factory. */
    @Inject
    RegulationGroupFactory regulationGroupFactory;
    
    /** The regulation group repository. */
    @Inject
    RegulationGroupRepository regulationGroupRepository;

    /**
     * Save technical group.
     */
    @Test
    public void saveTechnicalGroup() {
        TechnicalGroup techGroup = technicalGroupFactory.createTechGroup();
        techGroup.setLabel("TestTG");
        techGroup.setDescription("This is technicalGroup");
        TechnicalGroup newObj = technicalGroupService.saveTechGroup(techGroup);
        assertNotNull(newObj.getEntityId());
    }

    /**
     * Save edited technical group.
     */
    @Test
    public void saveEditedTechnicalGroup() {

        TechnicalGroup techGroup = technicalGroupFactory.createTechGroup();
        Status status = statusFactrory.createStatus(ConstantsApp.DRAFT);
        String samplingLabel = "Sampling Label";
        Date creationDate = new Date();
        Date modificationDate = new Date();
        boolean newVersion = false;
        RegulationGroup regulationGroup = regulationGroupFactory.createRegulationGroup();
        Status newStatusObj = statusRepository.saveStatus(status);
        assertNotNull(newStatusObj.getEntityId());
        RegulationGroup newRegObject = regulationGroupRepository.saveRegulationGroup(regulationGroup);
        assertNotNull(newRegObject.getEntityId());
        TechnicalGroup newTechObj = technicalGroupService.saveEditedTechnicalGroup(techGroup, newStatusObj, newVersion, samplingLabel, creationDate,
                newRegObject);
        assertNotNull(newTechObj.getEntityId());
        newVersion = true;
        newTechObj = technicalGroupService.saveEditedTechnicalGroup(techGroup, newStatusObj, newVersion, samplingLabel, creationDate, newRegObject);
        assertNotNull(newTechObj.getEntityId());

    }

    /**
     * Delete technical group.
     */
    @Test
    public void deleteTechnicalGroup() {
        List<TechnicalCase> technicalCaseList = new ArrayList<>();
        TechnicalGroup techGroup = technicalGroupFactory.createTechGroup();
        TechnicalCase technicalCase = technicalCaseFactory.createTechCase();
        TechnicalGroup newTechObj = techGroupRepository.saveTechGroup(techGroup);
        assertNotNull(newTechObj.getEntityId());
        technicalCase.setTechnicalGroup(newTechObj);
        TechnicalCase newTechCaseObj = technicalCaseRepository.saveTechCase(technicalCase);
        assertNotNull(newTechCaseObj.getEntityId());
        technicalCaseList.add(newTechCaseObj);
        technicalGroupService.deleteTechnicalGroup(technicalCaseList, newTechObj.getEntityId());
        TechnicalGroup technicalGroup = techGroupRepository.lodTechnicalGroup(newTechObj.getEntityId());
        assertEquals(technicalGroup, null);
        technicalCase = technicalCaseRepository.loadTechnicalCase(technicalCaseList.get(0).getEntityId());
        assertEquals(technicalCase.getTechnicalGroup(), null);
    }

}
