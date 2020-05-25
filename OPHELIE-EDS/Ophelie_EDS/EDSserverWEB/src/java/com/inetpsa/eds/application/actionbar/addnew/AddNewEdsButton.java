package com.inetpsa.eds.application.actionbar.addnew;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button creates a new EDS record.
 * 
 * @author Geometric Ltd.
 */
public class AddNewEdsButton extends NativeButton {
    // PUBLIC
    /**
     * Public parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public AddNewEdsButton(final EdsApplicationController controller) {
        super(controller.getBundle().getString("button-create"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                controller.queryNewEds(controller.getCurrentProject());
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
     * Initialize New Eds button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/addNewEds.png"));
        setDescription(controller.getBundle().getString("button-create-tt"));
    }
}
