package com.inetpsa.eds.tools.uri;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsRight;
import com.vaadin.ui.Window.Notification;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Fragment handler to access the administration part of the application.
 * 
 * @author Geometric Ltd.
 */
public class FH_Admin extends A_FragmentHandler implements Serializable {
    // PUBLIC
    /**
     * parameterized constructor
     * 
     * @param controller Controller of Eds application
     */
    public FH_Admin(EdsApplicationController controller) {
        super(ADMIN_FRAGMENT_KEY);
        this.controller = controller;
        init();
    }

    @Override
    public HashMap<String, String> parseFragmentResidue(String fragmentResidue) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void handleWithParameters(HashMap<String, String> parameters) {
        if (controller.userHasSufficientRights(EdsRight.APP_GENERAL_ACCESS_ADMIN)) {
            controller.showAdminPage();
        } else {
            controller
                    .getApplication()
                    .getMainWindow()
                    .showNotification("Accès interdit", "Vous n'avez pas les droits pour accéder à l'administration.",
                            Notification.TYPE_ERROR_MESSAGE);
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold EDS application controller value.
     */
    private EdsApplicationController controller;

    /**
     * Initialize admin fragment handler.
     */
    private void init() {
    }
}
