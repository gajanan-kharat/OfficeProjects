package com.inetpsa.eds.application.actionbar.editform;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button opens the associated read-only form into editable form.
 * 
 * @author Geometric Ltd.
 */
public class EditFormButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param editListener Listener to edit the form
     * @param controller Controller of EDS application
     */
    public EditFormButton(final I_Editable editListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-modif"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                editListener.edit();
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
     * Initialize Edit form button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/edit.png"));
        setDescription(controller.getBundle().getString("button-modif-tt"));
    }
}
