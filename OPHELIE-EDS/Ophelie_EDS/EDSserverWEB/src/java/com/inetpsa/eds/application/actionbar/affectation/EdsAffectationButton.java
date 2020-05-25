package com.inetpsa.eds.application.actionbar.affectation;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button assign the EDS.
 * 
 * @author Geometric Ltd.
 */
public class EdsAffectationButton extends NativeButton {
    /**
     * Public parameterized constructor
     * 
     * @param affectListener Listener to assign EDS
     * @param controller Controller of EDS application
     */
    public EdsAffectationButton(final I_Affectable affectListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-affectation"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                affectListener.affect();
            }
        });
    }

    // Private
    /**
     * Variable to hold EDS application controller value
     */
    private EdsApplicationController controller;

    /**
     * Initialize Assign EDS button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/affectEds.png"));
        setDescription(controller.getBundle().getString("button-affectation-tt"));
    }
}
