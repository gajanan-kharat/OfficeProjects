/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.dao;

import com.inetpsa.eds.dao.model.EdsProject;
import java.util.Set;

/**
 * Interface for Project specific data
 * 
 * @author Geometric Ltd.
 */
public interface I_ProjectSpecificData extends I_FormData {
    // PUBLIC
    /**
     * Add project-specific data in the form. Example: table consumption cycle to form the PSA measurements.
     * 
     * @param project project destination extension
     */
    public void addProjectReconductionData(EdsProject project);

    /**
     * Removes the project-specific data in the form. Example: table consumption cycle to form the PSA measurements.
     * 
     * @param project project to remove
     */
    public void removeProjectReconductionData(EdsProject project);

    /**
     * This method returns the set of projects
     * 
     * @return Set of Projects
     */
    public Set getEdsProjects();
}
