package com.inetpsa.eds.application.actionbar.reconduct;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button renew the EDS with modification.
 * 
 * @author Geometric Ltd.
 */
public class ReconductWithModifButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized consctuctor
     * 
     * @param reconductListener Listener to renew the EDS
     * @param controller Controller of EDS application
     */
    public ReconductWithModifButton(final I_Reconductable reconductListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-rec-modif"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                reconductListener.reconductWithModif();
            }
        });
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold EDS appliation controller value
     */
    private EdsApplicationController controller;

    /**
     * Initialize Reconduct With mofication Button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/reconductWithModif.png"));
        setDescription(controller.getBundle().getString("app-eds-reconduct-eds-with-modif"));
    }
}
