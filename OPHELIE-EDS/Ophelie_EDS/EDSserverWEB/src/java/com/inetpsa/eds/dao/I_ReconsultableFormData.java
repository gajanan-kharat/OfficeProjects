/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.dao;

import com.inetpsa.eds.dao.model.EdsEds;
import java.util.HashMap;

/**
 * Interface for reconsultable form data
 * 
 * @author Geometric Ltd.
 */
public interface I_ReconsultableFormData extends I_FormData {
    // PUBLIC
    /**
     * During the implementation of this feature, you must be careful to create full copies And not just reference copies if necessary. Only data must
     * be copied to consult again. This method is used by the functionality for reconsultation.
     * 
     * @param eds Eds details
     * @param copiesMap Map of ID and reconsultable classes.
     * @return a copy of the form.
     */
    public I_FormData getReconsultableCopy(EdsEds eds, HashMap<String, Object> copiesMap);
}
