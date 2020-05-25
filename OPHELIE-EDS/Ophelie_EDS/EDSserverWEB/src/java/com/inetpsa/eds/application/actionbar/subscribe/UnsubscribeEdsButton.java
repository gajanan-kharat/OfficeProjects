package com.inetpsa.eds.application.actionbar.subscribe;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button used to unsubscribe from EDS
 * 
 * @author Geometric Ltd.
 */
public class UnsubscribeEdsButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param subscribeListener Listener to save EDS
     * @param controller Controller of EDS application
     */
    public UnsubscribeEdsButton(final I_Subscribable subscribeListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-no-ab"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                subscribeListener.unsubscribe();
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
     * Initialize unsubscribe EDS button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/unsubscribe.png"));
        setDescription(controller.getBundle().getString("button-no-ab-tt"));
    }
}
