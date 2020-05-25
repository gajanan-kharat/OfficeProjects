package com.inetpsa.eds.tools.uri;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsRight;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Fragment handler for access to projects.
 * 
 * @author Geometric Ltd.
 */
public class FH_EdsProject extends A_FragmentHandler implements Serializable {
    // PUBLIC
    /**
     * parameterized constructor
     * 
     * @param controller Controller of Eds application
     */
    public FH_EdsProject(EdsApplicationController controller) {
        super(PROJECT_FRAGMENT_KEY);
        this.controller = controller;
        init();
    }

    @Override
    public HashMap<String, String> parseFragmentResidue(String fragmentResidue) throws IllegalArgumentException {
        HashMap<String, String> parameters = super.parseFragmentResidue(fragmentResidue);

        if (!parameters.containsKey("id")) {
            throw new IllegalArgumentException("URL mal formée.");
        }

        return parameters;
    }

    @Override
    public void handleWithParameters(HashMap<String, String> parameters) {
        if (controller.userHasSufficientRights(EdsRight.APP_GENERAL_ACCESS_PROJECT)) {
            controller.showProject(parameters.get("id"));
        } else {
            controller.showError("Accès interdit", "Vous n'avez pas les droits pour accéder à ce projet.");
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable that hold controller of EDS application.
     */
    private EdsApplicationController controller;

    /**
     * Initialize Eds project fragment handler.
     */
    private void init() {
    }

}
