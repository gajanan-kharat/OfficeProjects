package com.inetpsa.eds.application.actionbar.abort;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button restore the discarded EDS.
 * 
 * @author Geometric Ltd.
 */
public class UnabortEdsButton extends NativeButton {
    /**
     * Public parameterized constructor
     * 
     * @param unabortListener Listener for restoring aborted EDS
     * @param controller Controller of EDS application
     */
    public UnabortEdsButton(final I_Abortable unabortListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-reta"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                unabortListener.unabort();
            }
        });
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold EDS Application controller value
     */
    private EdsApplicationController controller;

    /**
     * Initialize restore EDS button.
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/abort.png"));
        setDescription(controller.getBundle().getString("button-reta-tt"));
    }
}
