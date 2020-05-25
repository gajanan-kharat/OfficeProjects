package com.inetpsa.eds.application.actionbar.reconduct;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button renew the EDS without modification.
 * 
 * @author Geometric Ltd.
 */
public class ReconductWithoutModifButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param reconductListener Listener to renew the EDS
     * @param controller Controller of EDS application
     */
    public ReconductWithoutModifButton(final I_Reconductable reconductListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("current-conso-list-alim-recond-button"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                reconductListener.reconductWithoutModif();
            }
        });
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold EDS application controller value
     */
    private EdsApplicationController controller;

    /**
     * Initialize Reconduct Without mofication Button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/reconductWithoutModif.png"));
        setDescription(controller.getBundle().getString("app-eds-reconduct-eds-without-modif"));
    }
}
