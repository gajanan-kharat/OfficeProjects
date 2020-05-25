package com.inetpsa.eds.application.actionbar.reconsult;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;

/**
 * This button helps to reconsult the EDS.
 * 
 * @author Geometric Ltd.
 */
public class ReconsultButton extends NativeButton {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param reconsultListener Listener to reconsult EDs
     * @param controller Controller of EDS application
     */
    public ReconsultButton(final I_Reconsultable reconsultListener, EdsApplicationController controller) {
        super(controller.getBundle().getString("button-reconsult"));
        this.controller = controller;
        init();

        addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                reconsultListener.reconsult();
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
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/reconsult.png"));
        setDescription(controller.getBundle().getString("button-reconsult-tt"));
    }
}
