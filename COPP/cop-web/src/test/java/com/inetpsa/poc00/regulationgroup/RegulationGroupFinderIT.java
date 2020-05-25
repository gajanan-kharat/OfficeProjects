/*
 * Creation : Feb 1, 2017
 */
package com.inetpsa.poc00.regulationgroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupFactory;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLFactory;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupFactory;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupRepresentation;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupRepresentation;

@RunWith(SeedITRunner.class)
public class RegulationGroupFinderIT {

    @Inject
    RegulationGroupRepository regulationRepo;
    @Inject
    RegulationGroupFactory regulationFactory;
    @Inject
    RegulationGroupFinder regulationGroupFinder;
    @Inject
    TechGroupRepository technicalGroupRepo;
    @Inject
    TechGroupFactory technicalGroupFactory;
    @Inject
    RGValuedESDependentTCLFactory rgValuedESFactory;
    @Inject
    RGValuedESDependentTCLRepository rgValuedEsRepo;

    @Test
    public void getRegulationGroup() {

        RegulationGroup regulationGroup = regulationFactory.createRegulationGroup();
        regulationGroup.setLabel("RegulationGroup");
        RegulationGroup savedRegulation = regulationRepo.saveRegulationGroup(regulationGroup);
        List<RegulationGroupRepresentation> regulationGroupRepresentList = regulationGroupFinder.getRegulationGroup(savedRegulation.getLabel());
        assertNotNull(regulationGroupRepresentList);
    }

    @Test
    public void getMaxVersionForRGLabel() {
        String previousVersion, nextVersion;
        RegulationGroup regulationGroup = regulationFactory.createRegulationGroup();
        regulationGroup.setLabel("RegulationGroup");
        previousVersion = "1.0";
        regulationGroup.setVersion(previousVersion);
        RegulationGroup savedRegulation = regulationRepo.saveRegulationGroup(regulationGroup);
        nextVersion = "3.0";
        savedRegulation.setVersion(nextVersion);
        RegulationGroup savedRg = regulationRepo.saveRegulationGroup(savedRegulation);
        Double maxVersion = regulationGroupFinder.getMaxVersionForRGLabel(savedRg.getLabel());
        assertEquals(maxVersion, new Double(nextVersion));

    }

    @Test
    public void getSelectedTechnicalGroupsForRg() {

        RegulationGroup regulationGroup = regulationFactory.createRegulationGroup();
        RegulationGroup savedRegulation = regulationRepo.saveRegulationGroup(regulationGroup);
        TechnicalGroup technicalGroup = technicalGroupFactory.createTechGroup();
        technicalGroup.setRegulationGroup(savedRegulation);
        TechnicalGroup savedTechnicalGroup = technicalGroupRepo.saveTechGroup(technicalGroup);
        List<TechnicalGroupRepresentation> technicalGroupRepresentList = regulationGroupFinder
                .getSelectedTechnicalGroupsForRg(savedRegulation.getEntityId());
        assertNotNull(technicalGroupRepresentList);
    }

    @Test
    public void getRegulationGroupForTG() {
        RegulationGroup regulationGroup = regulationFactory.createRegulationGroup();
        RegulationGroup savedRegulation = regulationRepo.saveRegulationGroup(regulationGroup);
        TechnicalGroup technicalGroup = technicalGroupFactory.createTechGroup();
        technicalGroup.setRegulationGroup(savedRegulation);
        TechnicalGroup savedTechnicalGroup = technicalGroupRepo.saveTechGroup(technicalGroup);
        Set<TechnicalGroup> technicalGroupset = new HashSet();
        technicalGroupset.add(savedTechnicalGroup);
        savedTechnicalGroup.getRegulationGroup().setTechnicalGroups(technicalGroupset);
        technicalGroupRepo.saveTechGroup(savedTechnicalGroup);
        RegulationGroup regulationObj = regulationGroupFinder.getRegulationGroupForTG(savedTechnicalGroup.getEntityId());
        assertNotNull(regulationObj);

    }

    @Test
    public void findAllRegulationGroups() {
        RegulationGroup regulationGroup = regulationFactory.createRegulationGroup();
        RegulationGroup savedRegulation = regulationRepo.saveRegulationGroup(regulationGroup);
        RegulationGroup regulationGroupnew = regulationFactory.createRegulationGroup();
        RegulationGroup savedRegulationnew = regulationRepo.saveRegulationGroup(regulationGroupnew);
        List<RegulationGroupRepresentation> regulationRepresentList = regulationGroupFinder.findAllRegulationGroups();
        assertNotNull(regulationRepresentList);

    }
}
