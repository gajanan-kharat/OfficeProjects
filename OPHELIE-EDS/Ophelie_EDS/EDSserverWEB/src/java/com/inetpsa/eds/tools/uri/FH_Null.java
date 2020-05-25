package com.inetpsa.eds.tools.uri;

import com.inetpsa.eds.application.EdsApplicationController;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Fragment handler to access features without identifier (eg URL research)
 * 
 * @author Geometric Ltd.
 */
public class FH_Null extends A_FragmentHandler implements Serializable {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public FH_Null(EdsApplicationController controller) {
        super();
        this.controller = controller;
        init();
    }

    @Override
    public HashMap<String, String> parseFragmentResidue(String fragmentResidue) throws IllegalArgumentException {
        HashMap<String, String> parameters = super.parseFragmentResidue(fragmentResidue);

        return parameters;
    }

    @Override
    public void handleWithParameters(HashMap<String, String> parameters) {
        if (parameters == null) {
            return;
        }

        if (parameters.containsKey("search")) {
            controller.showAllEds(parameters);
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable that hold controller of EDS application.
     */
    private EdsApplicationController controller;

    /**
     * Initialize fragment handler for features without identifier.
     */
    private void init() {
    }

}
