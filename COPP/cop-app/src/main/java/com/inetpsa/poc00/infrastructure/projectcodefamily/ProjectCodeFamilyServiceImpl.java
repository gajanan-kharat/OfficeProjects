/*
 * Creation : Dec 30, 2016
 */
package com.inetpsa.poc00.infrastructure.projectcodefamily;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.projectcodefamily.ProjectCodeFamilyService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository;

/**
 * The Class ProjectCodeFamilyServiceImpl.
 */
public class ProjectCodeFamilyServiceImpl implements ProjectCodeFamilyService {

    /** The logger. */
    @Logging
    private Logger logger;

    /** The project code family repo. */
    @Inject
    ProjectCodeFamilyRepository projectCodeFamilyRepo;

    /** The trac service. */
    @Inject
    TraceabilityService tracService;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.projectcodefamily.ProjectCodeFamilyService#saveProjectFamily(com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily)
     */
    @Override
    public ProjectCodeFamily saveProjectFamily(ProjectCodeFamily projectCodeFamily) {

        ProjectCodeFamily pcfResponse;
        if (projectCodeFamily.getEntityId() == null) {

            // save
            pcfResponse = projectCodeFamilyRepo.saveProjectCodeFamily(projectCodeFamily);

            tracService.historyForSave(pcfResponse, ConstantsApp.COMMONGENOME_SCREEN_ID);
        } else {
            // update

            ProjectCodeFamily oldPCF = projectCodeFamilyRepo.loadProjectCodeFamily(projectCodeFamily.getEntityId());
            tracService.historyForUpdate(oldPCF, projectCodeFamily, ConstantsApp.COMMONGENOME_SCREEN_ID);
            pcfResponse = projectCodeFamilyRepo.persistProjectCodeFamily(projectCodeFamily);

        }
        return pcfResponse;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.projectcodefamily.ProjectCodeFamilyService#deleteProjectFamily(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public boolean deleteProjectFamily(Long pCFId) {
        ProjectCodeFamily objToDelete = projectCodeFamilyRepo.loadProjectCodeFamily(pCFId);

        if (objToDelete.getTvvList().isEmpty()) {
            int deletedrows = projectCodeFamilyRepo.deleteAll(pCFId);
            if (deletedrows > 0) {
                logger.info("Sucessfully deleted ProjectCodeFamily data");
                tracService.historyForDelete(objToDelete, ConstantsApp.COMMONGENOME_SCREEN_ID);
                return true;
            }
            logger.error(" Error occured while deleting data  :", pCFId);
            return false;
        }
        logger.warn("Can't delete ProjectCodeFamily as used in other table : foreign key constraint");
        return false;
    }

}
