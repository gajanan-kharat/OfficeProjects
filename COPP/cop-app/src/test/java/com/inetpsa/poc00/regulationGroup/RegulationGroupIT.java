/*
 * Creation : Dec 28, 2016
 */
package com.inetpsa.poc00.regulationGroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.application.regulationgroup.RegulationGroupService;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupFactory;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLFactory;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupFactory;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;

@RunWith(SeedITRunner.class)
public class RegulationGroupIT {

    @Inject
    RegulationGroupFactory regulationGroupFactory;

    @Inject
    RegulationGroupRepository regulationGroupRepository;

    @Inject
    RegulationGroupService regulationGroupService;

    @Inject
    TechGroupFactory technicalGroupFactory;

    @Inject
    TechGroupRepository technicalGroupRepository;

    @Inject
    RGValuedESDependentTCLFactory rgValuedFactory;

    @Inject
    RGValuedESDependentTCLRepository rgValuedRepository;

    @Test
    public void saveRegulationGroup() {

        RegulationGroup regulationGroup = regulationGroupFactory.createRegulationGroup();
        regulationGroup.setLabel("RegulationGroup");
        regulationGroup.setDescription("Description");
        RegulationGroup newRegulationObj = regulationGroupService.saveRegulationGroup(regulationGroup);
        assertNotNull(newRegulationObj.getEntityId());
        RegulationGroup rgWithSameLabel = regulationGroupFactory.createRegulationGroup();
        rgWithSameLabel.setLabel("RegulationGroup");
        RegulationGroup regObj = regulationGroupService.saveRegulationGroup(rgWithSameLabel);
        assertNotNull(regObj.getEntityId());

    }

    @Test
    public void deleteRegulationGroup() {

        List<TechnicalGroup> technicalGroupList = new ArrayList<>();
        TechnicalGroup techGroup = technicalGroupFactory.createTechGroup();
        RegulationGroup regulationGrp = regulationGroupFactory.createRegulationGroup();
        RegulationGroup newRegObj = regulationGroupRepository.saveRegulationGroup(regulationGrp);
        assertNotNull(newRegObj.getEntityId());
        techGroup.setRegulationGroup(newRegObj);
        TechnicalGroup newTgobj = technicalGroupRepository.saveTechGroup(techGroup);
        assertNotNull(newTgobj.getEntityId());
        technicalGroupList.add(newTgobj);
        regulationGroupService.deleteRegulationGroup(technicalGroupList, newRegObj.getEntityId());
        RegulationGroup loadedRgObj = regulationGroupRepository.loadRegulationGroup(newRegObj.getEntityId());
        assertEquals(loadedRgObj, null);
        regulationGrp = technicalGroupList.get(0).getRegulationGroup();
        assertEquals(regulationGrp, null);

    }

    @Test
    public void deleteTechnicalGroup() {
        TechnicalGroup techGroup = technicalGroupFactory.createTechGroup();
        RegulationGroup regulationGrp = regulationGroupFactory.createRegulationGroup();
        RegulationGroup newRegObj = regulationGroupRepository.saveRegulationGroup(regulationGrp);
        assertNotNull(newRegObj.getEntityId());
        techGroup.setRegulationGroup(newRegObj);
        TechnicalGroup newTgobj = technicalGroupRepository.saveTechGroup(techGroup);
        assertNotNull(newTgobj.getEntityId());
        regulationGroupService.deleteTechnicalGroup(newTgobj.getEntityId());
        TechnicalGroup loadedTg = technicalGroupRepository.lodTechnicalGroup(newTgobj.getEntityId());
        assertEquals(loadedTg.getRegulationGroup(), null);

    }

    @Test
    public void deleteOldRgValuedTCL() {
        List<RGValuedESDependentTCL> rgvaluedList = new ArrayList();
        RGValuedESDependentTCL rgValuedObj = rgValuedFactory.createRegGrpValuedESDTCL();
        RGValuedESDependentTCL newObj = rgValuedRepository.saveRegGrpValEmStdDepTCL(rgValuedObj);
        rgvaluedList.add(newObj);
        regulationGroupService.deleteOldRgValuedTCL(rgvaluedList);
        RGValuedESDependentTCL loadedRgValuedObj = rgValuedRepository.loadRGValuedESDependentTCL(newObj.getEntityId());
        assertEquals(loadedRgValuedObj, null);

    }

}
