/*
 * Creation : Jan 30, 2017
 */
package com.inetpsa.poc00.technicalgroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupFactory;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseFactory;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupFactory;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.tvv.TVVFactory;
import com.inetpsa.poc00.domain.tvv.TVVRepository;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupRepresentation;

@RunWith(SeedITRunner.class)
public class TechnicalGroupFinderIT {

    @Inject
    TechGroupFactory technicalGroupFactory;
    @Inject
    TechGroupRepository technicalGroupRepo;

    @Inject
    TechnicalGroupFinder techGroupFinder;

    @Inject
    RegulationGroupRepository regulationGroupRepo;

    @Inject
    RegulationGroupFactory regulationGroupFactory;

    @Inject
    TechCaseRepository techcaseRepo;

    @Inject
    TechCaseFactory techCaseFactory;

    @Inject
    TVVRepository tvvRepo;

    @Inject
    TVVFactory tvvFactory;
    @Inject
    RegulationGroupFactory regulationFactory;

    @Inject
    RegulationGroupRepository regulationRepo;

    @Inject
    EmissionStandardFactory emmissionStandarFactory;

    @Inject
    EmissionStandardRepository emissionStandardRepo;

    @Test
    public void getTechnicalGroup() {
        TechnicalGroup technicalGroup = technicalGroupFactory.createTechGroup();
        technicalGroup.setLabel("TechnicalGroup");
        TechnicalGroup savedTechGroup = technicalGroupRepo.saveTechGroup(technicalGroup);
        List<TechnicalGroupRepresentation> techGroupRepresentList = techGroupFinder.getTechnicalGroup(savedTechGroup.getLabel());
        assertNotNull(techGroupRepresentList);
    }

    // @Test
    // public void findTechnicalGroupById() {
    // TechnicalGroup technicalGroup = technicalGroupFactory.createTechGroup();
    // technicalGroup.setLabel("TechnicalGroup");
    // TechnicalGroup savedTechGroup = technicalGroupRepo.saveTechGroup(technicalGroup);
    // TechnicalGroupRepresentation techGroupRepresent = techGroupFinder.findTechnicalGroupById(savedTechGroup.getEntityId());
    // assertNotNull(techGroupRepresent);
    // }

    @Test
    public void getMaxVersion() {
        String previousVersion, nextVersion;
        TechnicalGroup technicalGroup = technicalGroupFactory.createTechGroup();
        technicalGroup.setLabel("TechnicalGroup");
        previousVersion = "1.0";
        technicalGroup.setVersion(previousVersion);
        TechnicalGroup savedTechGroup = technicalGroupRepo.saveTechGroup(technicalGroup);
        nextVersion = "3.0";
        savedTechGroup.setVersion(nextVersion);
        TechnicalGroup savedTg = technicalGroupRepo.saveTechGroup(savedTechGroup);
        Double maxVersion = techGroupFinder.getMaxVersionForTGLabel(savedTg.getLabel());
        assertEquals(maxVersion, new Double(nextVersion));
    }

    @Test
    public void getTechnicalGroupToDelete() {
        RegulationGroup regulation = regulationGroupFactory.createRegulationGroup();
        RegulationGroup savedRegulation = regulationGroupRepo.saveRegulationGroup(regulation);
        TechnicalGroup technicalGroup = technicalGroupFactory.createTechGroup();
        technicalGroup.setRegulationGroup(savedRegulation);
        TechnicalGroup savedTechnicalGroup = technicalGroupRepo.saveTechGroup(technicalGroup);
        Set<TechnicalGroup> techGroupSet = new HashSet();
        techGroupSet.add(savedTechnicalGroup);
        savedRegulation.setTechnicalGroups(techGroupSet);
        List<TechnicalGroup> techGroupList = techGroupFinder.getTechnicalGroupToDelete(savedRegulation.getEntityId());
        assertNotNull(techGroupList);

    }
    //
    // @Test
    // public void getTvvsForTechnicalGroup() {
    // TVV tvv = tvvFactory.createTVV();
    // TVV savedTvv = tvvRepo.saveTVV(tvv);
    // TechnicalGroup techGroup = technicalGroupFactory.createTechGroup();
    // TechnicalGroup savedTechGroup = technicalGroupRepo.saveTechGroup(techGroup);
    // TechnicalCase technicalCase = techCaseFactory.createTechCase();
    // technicalCase.setTvv(savedTvv);
    // technicalCase.setTechnicalGroup(savedTechGroup);
    // techcaseRepo.saveTechCase(technicalCase);
    //
    // }

    @Test
    public void getTechnicalGroupForTechnicalCase() {

        TechnicalGroup technicalGroup = technicalGroupFactory.createTechGroup();
        TechnicalGroup savedTechGroup = technicalGroupRepo.saveTechGroup(technicalGroup);
        TechnicalCase techCase = techCaseFactory.createTechCase();
        techCase.setTechnicalGroup(savedTechGroup);
        TechnicalCase savedTechCase = techcaseRepo.saveTechCase(techCase);
        Set<TechnicalCase> technicalCaseSet = new HashSet<>();
        technicalCaseSet.add(savedTechCase);
        savedTechGroup.setTechnicalCase(technicalCaseSet);
        technicalGroupRepo.saveTechGroup(savedTechGroup);
        TechnicalGroup techGroupObj = techGroupFinder.getTechnicalGroupForTechnicalCase(savedTechCase.getEntityId());
        assertNotNull(techGroupObj);
    }

    @Test
    public void getRegulationGroup() {

        EmissionStandard emission = emmissionStandarFactory.createEmissonStandard();
        EmissionStandard savedEmission = emissionStandardRepo.saveEmissionStandard(emission);
        RegulationGroup regulationGroup = regulationFactory.createRegulationGroup();
        regulationGroup.setEmissionStandardforRg(savedEmission);
        RegulationGroup savedRegulation = regulationRepo.saveRegulationGroup(regulationGroup);
        TechnicalGroup technicalGroup = technicalGroupFactory.createTechGroup();
        TechnicalGroup savedTechGroup = technicalGroupRepo.saveTechGroup(technicalGroup);
        TechnicalCase techCase = techCaseFactory.createTechCase();
        techCase.setEmissionStandard(savedEmission);
        techCase.setTechnicalGroup(savedTechGroup);
        TechnicalCase savedTechCase = techcaseRepo.saveTechCase(techCase);
        Set<TechnicalCase> technicalCaseSet = new HashSet();
        technicalCaseSet.add(savedTechCase);
        savedTechGroup.setTechnicalCase(technicalCaseSet);
        savedTechGroup.setRegulationGroup(savedRegulation);
        TechnicalGroup savedTg = technicalGroupRepo.saveTechGroup(savedTechGroup);
        Set<TechnicalGroup> technicalGroupSet = new HashSet();
        technicalGroupSet.add(savedTg);
        savedRegulation.setTechnicalGroups(technicalGroupSet);
        regulationRepo.saveRegulationGroup(savedRegulation);
        List<RegulationGroup> regulationGroupObjList = techGroupFinder.getRegulationGroup(savedTechGroup.getEntityId());
        assertNotNull(regulationGroupObjList.get(0));
    }

}
