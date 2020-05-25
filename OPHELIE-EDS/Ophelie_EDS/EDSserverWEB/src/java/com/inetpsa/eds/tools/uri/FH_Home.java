package com.inetpsa.eds.tools.uri;

import com.inetpsa.eds.application.EdsApplicationController;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Fragment handler to access the home page.
 * 
 * @author Geometric Ltd.
 */
public class FH_Home extends A_FragmentHandler implements Serializable {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public FH_Home(EdsApplicationController controller) {
        super(HOME_FRAGMENT_KEY);
        this.controller = controller;
        init();
    }

    @Override
    public HashMap<String, String> parseFragmentResidue(String fragmentResidue) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void handleWithParameters(HashMap<String, String> parameters) {
        controller.showHomePage();
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
