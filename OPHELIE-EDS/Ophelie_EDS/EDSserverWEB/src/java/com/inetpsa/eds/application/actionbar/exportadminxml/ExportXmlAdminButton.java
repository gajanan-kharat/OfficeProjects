package com.inetpsa.eds.application.actionbar.exportadminxml;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button Export the Data in XML format for admin user.
 * 
 * @author Geometric Ltd.
 */
public class ExportXmlAdminButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param exportXmlAdminListener Listener to export Data in XML format
     * @param controller Controller of EDS application
     */
    public ExportXmlAdminButton(final I_XmlAdminExportable exportXmlAdminListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-exp-admin"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                exportXmlAdminListener.exportAdminXml();
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
     * Initialize Export XML button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/export.png"));
        setDescription(controller.getBundle().getString("button-exp-admin-tt"));
    }
}
