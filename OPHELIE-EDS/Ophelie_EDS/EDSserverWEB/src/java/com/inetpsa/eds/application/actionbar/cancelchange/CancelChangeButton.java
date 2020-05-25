package com.inetpsa.eds.application.actionbar.cancelchange;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button is used to exit an edit form without saving changes
 * 
 * @author Geometric Ltd.
 */
public class CancelChangeButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param cancelListener Listener to Cancel the editing of form
     * @param controller Controller of EDS application
     */
    public CancelChangeButton(final I_Cancelable cancelListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-cancel"));
        this.controller = controller;
        init();
        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                cancelListener.cancel();
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
     * Initialize Cancel change button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/cancel.png"));
        setDescription(controller.getBundle().getString("button-cancel"));
    }
}
