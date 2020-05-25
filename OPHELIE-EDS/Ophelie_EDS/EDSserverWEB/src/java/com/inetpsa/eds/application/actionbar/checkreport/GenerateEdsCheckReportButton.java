/*
 * Creation : 13 mai 2015
 */
package com.inetpsa.eds.application.actionbar.checkreport;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.NativeButton;

/**
 * Button to generate a check report on an EDS Sheet
 * 
 * @author Idir MEZIANI
 */
public class GenerateEdsCheckReportButton extends NativeButton {

    public GenerateEdsCheckReportButton(final I_CheckReport checkReportListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("check-report-btn"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                checkReportListener.generateCheckReport();
            }
        });
    }

    // PRIVATE

    private static final Resource CHECKREPORT_RESSOURCE = ResourceManager.getInstance().getThemeIconResource("icons/check.png");
    private EdsApplicationController controller;

    private void init() {
        setIcon(CHECKREPORT_RESSOURCE);
        setDescription(controller.getBundle().getString("check-report-button-tt"));
    }
}
