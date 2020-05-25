package com.inetpsa.eds.application.actionbar.discardchange;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button resets an edit form with the original data.
 * 
 * @author Geometric Ltd.
 */
public class DiscardChangeButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param discardListener Listener to reset edit form
     * @param controller Controller of EDS application
     */
    public DiscardChangeButton(final I_Discardable discardListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-init"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                discardListener.discard();
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
     * Initialize Discard change button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/discard.png"));
        setDescription(controller.getBundle().getString("button-init-data"));
    }
}
