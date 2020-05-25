//COPYRIGHT PSA Peugeot Citroen 2014
/**********************************************************************************************************
 *
 * FILE NAME	  : FH_Logout.java
 *
 * SOCIETE        : PSA
 * PROJET         : EDS B2B Evolutions
 * VERSION        : V1.0
 * DATE           : 02/04/2012
 *
 * AUTEUR         : GEOMETRIC LTD.
 *
 * DESCRIPTION    : Fragment handler to logout from EDS application.
 *					
 **********************************************************************************************************/
package com.inetpsa.eds.tools.uri;

import java.io.Serializable;
import java.util.HashMap;

import com.inetpsa.eds.application.EdsApplicationController;

/**
 * Fragment handler to logout from EDS application
 * 
 * @author Geometric Ltd.
 */
public class FH_Logout extends A_FragmentHandler implements Serializable {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public FH_Logout(EdsApplicationController controller) {
        super(LOGOUT_FRAGMENT_KEY);
        this.controller = controller;
        init();
    }

    @Override
    public HashMap<String, String> parseFragmentResidue(String fragmentResidue) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void handleWithParameters(HashMap<String, String> parameters) {
        controller.getApplication().close();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable that hold controller of EDS application.
     */
    private EdsApplicationController controller;

    /**
     * Initialize home fragment handler.
     */
    private void init() {
    }
}
