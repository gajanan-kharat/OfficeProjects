package com.inetpsa.eds.application.popup;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.EDSdao;
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
 * This class is used for creating Error window while Validating Consolidate stage.
 * 
 * @author Geometric Ltd.
 */
public class ValidationErrorWindow extends A_EdsWindow {

    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds edsSource;

    /**
     * constant hold value of Error logo
     */
    private static final Resource ERROR_LOGO = ResourceManager.getInstance().getThemeIconResource("icons/error.png");

    /**
     * Parameterized constructor
     * 
     * @param controller Eds Application controller
     * @param eds EdsEds
     */
    public ValidationErrorWindow(EdsApplicationController controller, EdsEds eds) {
        super(controller.getBundle().getString("pop-error-title"));
        this.controller = controller;
        this.edsSource = eds;
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
    private VerticalLayout errorLayout;

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
     * This method is used to get the validation error pop-up window.
     * 
     * @return Component of validation error window.
     */
    private VerticalLayout getWarningLayout() {

        errorLayout = new VerticalLayout();
        errorLayout.setWidth("30%");
        errorLayout.setHeight("25%");
        errorLayout.setSpacing(true);
        HorizontalLayout messageLayout = new HorizontalLayout();
        Embedded vIMGtitle = new Embedded(null, ERROR_LOGO);
        messageLayout.addComponent(vIMGtitle);
        messageLayout.setComponentAlignment(vIMGtitle, Alignment.MIDDLE_LEFT);
        Label message = new Label();
        message.setCaption(controller.getBundle().getString("Validation-message-9"));
        messageLayout.addComponent(message);
        messageLayout.setComponentAlignment(message, Alignment.MIDDLE_RIGHT);
        errorLayout.addComponent(messageLayout);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button okButton = new Button(controller.getBundle().getString("Validation-OK"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        buttonLayout.addComponent(okButton);

        errorLayout.addComponent(buttonLayout);
        errorLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);

        return errorLayout;
    }

}
