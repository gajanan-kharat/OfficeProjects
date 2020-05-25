package com.inetpsa.eds.tools.uri;

import java.io.Serializable;
import java.util.HashMap;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsRight;

/**
 * Fragment handler for accessing EDS records.
 * 
 * @author Geometric Ltd.
 */
public class FH_EdsEds extends A_FragmentHandler implements Serializable {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public FH_EdsEds(EdsApplicationController controller) {
        super(EDS_FRAGMENT_KEY);
        this.controller = controller;
        init();
    }

    /**
     * This method parse the fragment of URI after # key.
     * 
     * @param fragmentResidue Fragment of URI
     * @return Map of fragment key and its value
     * @throws IllegalArgumentException Invalid argument
     */
    @Override
    public HashMap<String, String> parseFragmentResidue(String fragmentResidue) throws IllegalArgumentException {
        HashMap<String, String> parameters = super.parseFragmentResidue(fragmentResidue);

        if (!parameters.containsKey("ref")) {
            throw new IllegalArgumentException("URL mal formée.");
        }

        return parameters;
    }

    /**
     * This method works on URI parameters
     * 
     * @param parameters Map of parameters key and value
     */
    @Override
    public void handleWithParameters(HashMap<String, String> parameters) {
        EDSdao dao = EDSdao.getInstance();
        EdsEds eds = null;

        if (parameters.containsKey("v") && parameters.get("v").matches(VERSION_REGEXP)) {
            String versionValue = parameters.get("v");
            int dotIndex = versionValue.indexOf('.');
            int majorVersion = Integer.parseInt(versionValue.substring(0, dotIndex));
            int minorVersion = Integer.parseInt(versionValue.substring(dotIndex + 1));

            eds = dao.getEdsByRef(parameters.get("ref"), majorVersion, minorVersion);

        } else {

            eds = dao.getEdsByRef(parameters.get("ref"));

        }

        if (controller.getAuthenticatedUser().getEdsRole().isSupplier()) {
            if (controller.getAuthenticatedUser().getEdsSupplier().equals(eds.getEdsSupplier())) {
                controller.openEds(eds);
            } else {
                controller.showError("Accès interdit", "Vous n'avez pas les droits pour accéder à cette fiche.");
            }
        } else if (controller.getAuthenticatedUser().getEdsRole().isPerimeter()) {

            boolean isInPerimeter = dao.isEdsIn(eds, controller.getAuthenticatedUser().getEdsPerimeter());

            if (isInPerimeter) {
                controller.openEds(eds);
            } else {
                controller.showError("Accès interdit", "Vous n'avez pas les droits pour accéder à cette fiche.");
            }
        } else if (!controller.userHasSufficientRights(EdsRight.APP_EDS_SEE_PRIMARY_ORGANS) && eds.isBttbt()) {
            controller.showError("Accès interdit", "Vous n'avez pas les droits pour accéder à cette fiche.");
        } else if (!controller.userHasSufficientRights(EdsRight.APP_EDS_SEE_SECONDARY_ORGANS) && !eds.isBttbt()) {
            controller.showError("Accès interdit", "Vous n'avez pas les droits pour accéder à cette fiche.");
        } else {
            controller.openEds(eds);
        }

    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant variable to hold value of version regular expression.
     */
    private static final String VERSION_REGEXP = "\\d+\\.\\d+";

    /**
     * Variable that hold controller of EDS application.
     */
    private EdsApplicationController controller;

    /**
     * Initialize Eds record fragment handler.
     */
    private void init() {
    }
}
