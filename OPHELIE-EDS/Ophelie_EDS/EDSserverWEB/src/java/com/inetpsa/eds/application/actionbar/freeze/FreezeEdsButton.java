package com.inetpsa.eds.application.actionbar.freeze;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.NativeButton;

/**
 * Button to trigger an action of major versioning of the current record.
 * 
 * @author Geometric Ltd.
 */
public class FreezeEdsButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized consctuctor
     * 
     * @param freezeListener Listener to Freeze the EDS
     * @param controller Controller of EDS application
     */
    public FreezeEdsButton(final I_Freezable freezeListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-fix"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                freezeListener.freeze();
            }
        });
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to Define icon for FREEZE_RESOURCE
     */
    private static final Resource FREEZE_RESOURCE = ResourceManager.getInstance().getThemeIconResource("icons/freeze.png");
    /**
     * Variable to hold EDS appliation controller value
     */
    private EdsApplicationController controller;

    /**
     * Initialize Freeze Eds Buttom
     */
    private void init() {
        setIcon(FREEZE_RESOURCE);
        setDescription(controller.getBundle().getString("button-fix-tt"));
    }
}
