/*
 * Creation : 6 mai 2015
 */
package com.inetpsa.eds.application.actionbar.resetvalidation;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.NativeButton;

/**
 * Button to reset an Eds to the preliminary stage.
 * 
 * @author Idir MEZIANI
 */
public class ResetEdsValidationsButton extends NativeButton {

    public ResetEdsValidationsButton(final I_Resettable resetListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("validations-reset-button"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                resetListener.reset();
            }
        });

    }

    private static final Resource RESET_RESSOURCE = ResourceManager.getInstance().getThemeIconResource("icons/reinit.png");
    private EdsApplicationController controller;

    private void init() {
        setIcon(RESET_RESSOURCE);
        setDescription(controller.getBundle().getString("validations-reset-button-tt"));
    }
}
