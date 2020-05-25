package com.inetpsa.eds.application.content.eds.composants;

import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.ui.Label;

/**
 * This class provide comment component
 * 
 * @author Geometric Ltd.
 */
public class Comment extends Label {
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public Comment(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    // PRIVE
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Comment component
     */
    private void init() {
        addStyleName("comment");
        if (controller.getBrowserAndVersion().equals("IE7")) {
            setContentMode(Label.CONTENT_PREFORMATTED);
        }
    }
}
