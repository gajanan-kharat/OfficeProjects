package com.inetpsa.eds.tools.uri;

import com.inetpsa.eds.application.EdsApplicationController;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Fragment handler to access the user home page
 * 
 * @author Geometric Ltd.
 */
public class FH_UserParams extends A_FragmentHandler implements Serializable {
    // PUBLIC
    /**
     * parameterized constructor
     * 
     * @param controller Controller of Eds application
     */
    public FH_UserParams(EdsApplicationController controller) {
        super(USER_PARAMS_FRAGMENT_KEY);
        this.controller = controller;
        init();
    }

    @Override
    public HashMap<String, String> parseFragmentResidue(String fragmentResidue) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void handleWithParameters(HashMap<String, String> parameters) {
        controller.showUserParamsPage();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold EDS application controller value.
     */
    private EdsApplicationController controller;

    /**
     * Initialize user param fragment handler.
     */
    private void init() {
    }
}
