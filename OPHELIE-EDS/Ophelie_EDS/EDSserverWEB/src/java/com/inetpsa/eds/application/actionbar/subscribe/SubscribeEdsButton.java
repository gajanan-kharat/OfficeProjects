package com.inetpsa.eds.application.actionbar.subscribe;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button used to subscribe the EDS form
 * 
 * @author Geometric Ltd.
 */
public class SubscribeEdsButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param subscribeListener Listener to subscribe EDS
     * @param controller Controller of EDS application
     */
    public SubscribeEdsButton(final I_Subscribable subscribeListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-subs"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                subscribeListener.subscribe();
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
     * Initialize Subscribe EDS button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/subscribe.png"));
        setDescription(controller.getBundle().getString("app-project-menu-subscribe-eds"));
    }
}
