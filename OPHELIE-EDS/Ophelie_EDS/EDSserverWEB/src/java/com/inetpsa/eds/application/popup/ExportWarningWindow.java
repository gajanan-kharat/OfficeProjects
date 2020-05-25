package com.inetpsa.eds.application.popup;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This class is used for creating Warning window after Admin xml file is exported.
 * 
 * @author Geometric Ltd.
 */

public class ExportWarningWindow extends A_EdsWindow {

    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds edsSource;

    /**
     * constant hold value of Warning logo
     */
    private static final Resource WARNING_LOGO = ResourceManager.getInstance().getThemeIconResource("icons/warning.png");

    /**
     * Parameterized constructor
     * 
     * @param controller Eds Application Controller
     */
    public ExportWarningWindow(EdsApplicationController controller) {
        super(controller.getBundle().getString("eds-pop-warning-title"));
        this.controller = controller;
        init();
    }

    // PROTECTED
    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Window#close()
     */
    @Override
    protected void close() {
        super.close();
    }

    // PRIVATE

    /**
     * EDS Application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store layout of pop-up.
     */
    private VerticalLayout waringLayout;

    /**
     * This method is used to initialize this class.
     */
    private void init() {
        VerticalLayout layout = (VerticalLayout) getContent();
        layout.setSizeUndefined();
        layout.setMargin(true);
        layout.addComponent(getWarningLayout());
    }

    /**
     * This method is used to get the warning window.
     * 
     * @return Component of warning window.
     */
    private VerticalLayout getWarningLayout() {

        waringLayout = new VerticalLayout();
        waringLayout.setSpacing(true);
        waringLayout.setSizeUndefined();
        HorizontalLayout messageLayout = new HorizontalLayout();
        Embedded vIMGtitle = new Embedded(null, WARNING_LOGO);
        messageLayout.addComponent(vIMGtitle);
        messageLayout.setComponentAlignment(vIMGtitle, Alignment.MIDDLE_LEFT);

        VerticalLayout messageVertLayout = new VerticalLayout();
        messageLayout.setSpacing(true);
        Label message1 = new Label();
        message1.setCaption(controller.getBundle().getString("Admin-export1"));
        Label message2 = new Label();
        message2.setCaption(controller.getBundle().getString("Admin-export2"));
        Label message3 = new Label();
        message3.setCaption(controller.getBundle().getString("Admin-export3"));
        messageVertLayout.addComponent(message1);
        messageVertLayout.addComponent(message2);
        messageVertLayout.addComponent(message3);
        messageVertLayout.setComponentAlignment(message1, Alignment.MIDDLE_LEFT);
        messageVertLayout.setComponentAlignment(message2, Alignment.MIDDLE_LEFT);
        messageVertLayout.setComponentAlignment(message3, Alignment.MIDDLE_LEFT);
        messageLayout.addComponent(messageVertLayout);
        messageLayout.setComponentAlignment(messageVertLayout, Alignment.MIDDLE_RIGHT);

        waringLayout.addComponent(messageLayout);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button okButton = new Button(controller.getBundle().getString("Validation-OK"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                close();
            }
        });

        buttonLayout.addComponent(okButton);

        waringLayout.addComponent(buttonLayout);
        waringLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);

        return waringLayout;
    }
}
