package com.inetpsa.eds.application.actionbar.exportxml;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button Export the Data in XML format.
 * 
 * @author Geometric Ltd.
 */
public class ExportXmlButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param exportXmlListener Listener to export Data in XML format
     * @param controller Controller of EDS application
     */
    public ExportXmlButton(final I_XmlExportable exportXmlListener, final EdsApplicationController controller) {
        super(controller.getBundle().getString("button-exp"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            private static final long serialVersionUID = 5247383406655483068L;

            public void buttonClick(ClickEvent event) {
                exportXmlListener.exportXml();
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
        setDescription(controller.getBundle().getString("button-exp-tt"));
    }
}
