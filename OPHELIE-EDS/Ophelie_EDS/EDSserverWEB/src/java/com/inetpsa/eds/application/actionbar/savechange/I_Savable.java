package com.inetpsa.eds.application.actionbar.savechange;

/**
 * This interface acts as a listener to Save the EDS
 * 
 * @author Geometric Ltd.
 */
public interface I_Savable {
    // PUBLIC
    /**
     * This method must pass an edit mode to display mode after recording changes .
     */
    public void save();
}
