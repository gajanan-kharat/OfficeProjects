package com.inetpsa.poc00.domain.projectcodefamily;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of ProjectCodeFamily.
 */

public interface ProjectCodeFamilyRepository extends GenericRepository<ProjectCodeFamily, Long> {

    /**
     * Saves the ProjectCodeFamily.
     * 
     * @param object the ProjectCodeFamily to save
     * @return the ProjectCodeFamily
     */
    ProjectCodeFamily saveProjectCodeFamily(ProjectCodeFamily object);

    /**
     * Persists the ProjectCodeFamily.
     * 
     * @param object the ProjectCodeFamily to persist
     * @return the project code family
     */
    ProjectCodeFamily persistProjectCodeFamily(ProjectCodeFamily object);

    /**
     * Delete all categories.
     * 
     * @param id the id
     * @return number of categories deleted
     */
    int deleteAll(long id);

    /**
     * Gets the all project code family.
     * 
     * @return the all project code family
     */
    List<ProjectCodeFamily> getAllProjectCodeFamily();

    /**
     * Load project code family.
     *
     * @param projectFamilyId the project family id
     * @return the project code family
     */
    ProjectCodeFamily loadProjectCodeFamily(long projectFamilyId);

    /**
     * Gets the projec code familyby label.
     *
     * @param projectFamilyLabel the project family label
     * @return the projec code familyby label
     */
    public ProjectCodeFamily getProjecCodeFamilybyLabel(String projectFamilyLabel);

}
