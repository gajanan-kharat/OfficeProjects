package com.inetpsa.eds.application.actionbar.export;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button launch the export window, allowing to either export in Excel or in XML.
 */
public class ExportButton extends NativeButton {

    private static final long serialVersionUID = -18377876502791678L;

    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param exportXmlListener Listener to export Data in XML format
     * @param controller Controller of EDS application
     */
    public ExportButton(final I_Exportable exportListener, final EdsApplicationController controller) {
        super(controller.getBundle().getString("button-exp"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            private static final long serialVersionUID = 5247383406655483068L;

            public void buttonClick(ClickEvent event) {
                exportListener.showExport();
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
     * Initialize Export button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/export.png"));
        setDescription(controller.getBundle().getString("button-exp-tt"));
    }
}
