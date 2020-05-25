package com.inetpsa.eds.application.actionbar.close;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button closes the open EDS
 * 
 * @author Geometric Ltd.
 */
public class CloseEdsButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param closeListener Listener to close EDS
     * @param controller Controller of EDS application
     */
    public CloseEdsButton(final I_Closable closeListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-close"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                closeListener.close();
            }
        });
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold EdsApplicationController value
     */
    private EdsApplicationController controller;

    /**
     * Initialize Close button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/closeEds.png"));
        setDescription(controller.getBundle().getString("button-close-tt"));
    }
}
