package com.inetpsa.eds.application.actionbar.reconduct;

/**
 * This interface acts as listener to renew the EDS.
 * 
 * @author Geometric Ltd.
 */
public interface I_Reconductable {
    // PUBLIC
    /**
     * This method is responsible for making a renewal without modification
     */
    public void reconductWithoutModif();

    /**
     * This method is responsible for making a renewal with modification
     */
    public void reconductWithModif();
}
