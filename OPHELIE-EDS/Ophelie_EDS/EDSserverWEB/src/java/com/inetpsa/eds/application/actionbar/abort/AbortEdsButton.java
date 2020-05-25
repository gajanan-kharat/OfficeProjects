package com.inetpsa.eds.application.actionbar.abort;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.localisation.BundleManager;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button Discard EDS.
 * 
 * @author Geometric Ltd.
 */
public class AbortEdsButton extends NativeButton {
    // PRIVATE
    /**
     * Variable to hold EDS Application controller value
     */
    private EdsApplicationController controller;

    /**
     * Public parameterized constructor
     * 
     * @param abortListener Listener for aborting EDS
     * @param controller Controller of EDS application
     */
    public AbortEdsButton(final I_Abortable abortListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-canc"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                abortListener.abort();
            }
        });
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Abort Button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/abort.png"));
        setDescription(controller.getBundle().getString("app-project-menu-dicard-eds"));
    }
}
