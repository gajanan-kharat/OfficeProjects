/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.dao;

import com.inetpsa.eds.dao.model.EdsEds;
import java.util.HashMap;
import java.util.List;

/**
 * Interface for form data
 * 
 * @author Geometric Ltd.
 */
public interface I_FormData {
    // PUBLIC
    /**
     * During the implementation of this feature, you must be careful to create full copies and not just reference copies if necessary. This method is
     * used by the feature versioning.
     * 
     * @param eds Eds details
     * @param copiesMap Map to store copies that can be used in several forms, such as EdsSupply.
     * @return a copy of the form.
     */
    public I_FormData getCopy(EdsEds eds, HashMap<String, Object> copiesMap);

    /**
     * This method returns all objects to save
     * 
     * @return a list of all the objects to save the data model, the model itself understood.
     */
    public List getAllItemsToSave();
}
