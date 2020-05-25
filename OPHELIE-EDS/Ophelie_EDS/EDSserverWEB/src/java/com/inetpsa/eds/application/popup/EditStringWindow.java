package com.inetpsa.eds.application.popup;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.text.MessageFormat;

/**
 * This class is used to create pop-up for editing the name of labels.
 * <ul>
 * It performs the following operations:
 * <li>Create Edit String pop-up.</li>
 * <li>Add label and text box for rename.</li>
 * <li>Add buttons and button listeners for validation.</li>
 * </ul>
 * 
 * @author Geometric Ltd.
 */
public class EditStringWindow extends A_EdsWindow {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param oldValue Old value of the string to be updated.
     * @param windowTitle Title of the window pop-up.
     * @param labelValue Label value for the pop-up.
     * @param controller EDS application controller object.
     * @param listener Validation listener for the text field.
     */
    public EditStringWindow(String oldValue, String windowTitle, String labelValue, EdsApplicationController controller, ValidateListener listener) {
        super(windowTitle);
        this.value = oldValue;
        this.labelValue = labelValue;
        this.controller = controller;
        this.listener = listener;
        init();
    }

    /**
     * This class is used to validate the text box on the Edit String pop-up.
     * 
     * @author Geometric Ltd.
     */
    public static abstract class ValidateListener {
        /**
         * Abstract method to validate the new value.
         * 
         * @param newValue New value to be validated.
         */
        public abstract void onValidate(String newValue);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the value of the text.
     */
    private String value;
    /**
     * Variable to store the lable name.
     */
    private String labelValue;
    /**
     * Variable to store the validation listener.
     */
    private ValidateListener listener;
    /**
     * Variable to store the window title.
     */
    private String windowTitle;
    /**
     * Variable to store the EDS application controller.
     */
    private EdsApplicationController controller;

    /**
     * This method is used to initialize this class.
     * <ul>
     * It performs the following operations:
     * <li>Create Edit String pop-up.</li>
     * <li>Add label and text box for rename.</li>
     * <li>Add buttons and button listeners for validation.</li>
     * </ul>
     */
    private void init() {
        VerticalLayout mainLayout = (VerticalLayout) getContent();
        mainLayout.setMargin(true);

        GridLayout contentLayout = new GridLayout(2, 2);
        contentLayout.setSpacing(true);

        final TextField vTFstringValue = new TextField();
        vTFstringValue.setValue(value);
        vTFstringValue.setRequired(true);
        vTFstringValue.setRequiredError(MessageFormat.format(controller.getBundle().getString("eds-field-not-empty"), labelValue));
        contentLayout.addComponent(new Label(labelValue + " :"));

        contentLayout.addComponent(vTFstringValue, 1, 0);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);

        Button okButton = new Button(controller.getBundle().getString("Admin-proj-validate-button"));
        okButton.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (vTFstringValue.isValid()) {
                    listener.onValidate(vTFstringValue.getValue().toString());
                    close();
                } else {
                    showNotification(controller.getBundle().getString("eds-field-error"));
                }
            }
        });
        buttonsLayout.addComponent(okButton);

        Button cancelButton = new Button(controller.getBundle().getString("button-cancel"));
        cancelButton.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        buttonsLayout.addComponent(cancelButton);

        contentLayout.addComponent(buttonsLayout, 1, 1);
        contentLayout.setComponentAlignment(okButton, Alignment.TOP_RIGHT);

        mainLayout.addComponent(contentLayout);
        mainLayout.setExpandRatio(contentLayout, 1);
        mainLayout.setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setSizeUndefined();

        setResizable(false);
    }
}
