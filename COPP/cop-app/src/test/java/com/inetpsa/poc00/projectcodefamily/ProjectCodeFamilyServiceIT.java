/*
 * Creation : Jan 19, 2017
 */
package com.inetpsa.poc00.projectcodefamily;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.projectcodefamily.ProjectCodeFamilyService;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyFactory;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository;

@RunWith(SeedITRunner.class)
public class ProjectCodeFamilyServiceIT {

    @Inject
    ProjectCodeFamilyRepository projectCodeRepo;
    @Inject
    ProjectCodeFamilyFactory projectCodeFactory;
    @Inject
    ProjectCodeFamilyService projectCodeService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveProjectFamily() {
        ProjectCodeFamily projectCode = projectCodeFactory.createPCFamily();
        ProjectCodeFamily saveProjectCodeFamily = projectCodeService.saveProjectFamily(projectCode);
        ProjectCodeFamily loadedProjectCodeFamily = projectCodeRepo.loadProjectCodeFamily(saveProjectCodeFamily.getEntityId());
        assertNotNull(loadedProjectCodeFamily.getEntityId());
        loadedProjectCodeFamily.setProjectCodeLabel("ProjectCodeLabel");
        ProjectCodeFamily persistedProjectCode = projectCodeService.saveProjectFamily(loadedProjectCodeFamily);
        ProjectCodeFamily loadedpersitObj = projectCodeRepo.loadProjectCodeFamily(persistedProjectCode.getEntityId());
        assertEquals(loadedProjectCodeFamily.getEntityId(), loadedpersitObj.getEntityId());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteProjectFamily() {
        ProjectCodeFamily projectCode = projectCodeFactory.createPCFamily();
        projectCode.setVehicleFamilyLabel("SomeThing");
        projectCode.setTvvList(null);
        ProjectCodeFamily saveProjectCodeFamily = projectCodeService.saveProjectFamily(projectCode);
        projectCodeService.deleteProjectFamily(saveProjectCodeFamily.getEntityId());
        ProjectCodeFamily loadedProjectCodeFamily = projectCodeRepo.loadProjectCodeFamily(saveProjectCodeFamily.getEntityId());
        assertEquals(loadedProjectCodeFamily, null);

    }

}
