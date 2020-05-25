package com.inetpsa.eds.application.actionbar.chs;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button is used to exit an edit form by recording changes in the database.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class ExportEdsChsButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param saveListener Listener to save EDS
     * @param controller Controller of EDS application
     */
    public ExportEdsChsButton(final I_EdsChsExport saveListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-export-chs"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                saveListener.exportEdsChs();
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
     * Initialize Save Changes button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/export.png"));
        setDescription(controller.getBundle().getString("button-export-chs"));
    }
}
