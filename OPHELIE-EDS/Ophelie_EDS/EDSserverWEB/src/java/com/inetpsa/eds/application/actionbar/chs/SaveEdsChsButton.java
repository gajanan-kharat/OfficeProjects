package com.inetpsa.eds.application.actionbar.chs;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button is used to exit an edit form by recording changes in the database.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class SaveEdsChsButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param saveListener Listener to save EDS
     * @param controller Controller of EDS application
     */
    public SaveEdsChsButton(final I_EdsChsSavable saveListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-save"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                saveListener.saveEdsChs();
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
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/save.png"));
        setDescription(controller.getBundle().getString("button-save-tt"));
    }
}
