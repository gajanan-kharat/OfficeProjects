/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.application.popup;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsWording;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import java.util.Locale;

/**
 * This class is used to create pop-up for editing the wording of labels(ENGLISH & FRENCH).
 * <ul>
 * It performs the following operations:
 * <li>Create Edit Wording pop-up.</li>
 * <li>Add English and French labels and text boxes for renaming.</li>
 * <li>Add buttons and button listeners for validation.</li>
 * </ul>
 * 
 * @author Geometric Ltd.
 */
public class EditWordingWindow extends A_EdsWindow {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param wording Wording details to be modified.
     * @param list List to be updated on validate.
     * @param windowName Name of the pop-up window.
     * @param controller EDS Application controller object.
     */
    public EditWordingWindow(EdsWording wording, AbstractSelect list, String windowName, EdsApplicationController controller) {
        this(wording, list, windowName, null, controller);
    }

    /**
     * Parameterized constructor.
     * 
     * @param wording Wording details to be modified.
     * @param list List to be updated on validate.
     * @param windowName Name of the pop-up window.
     * @param listener Validation listener for the pop-up window.
     * @param controller EDS Application controller object.
     */
    public EditWordingWindow(EdsWording wording, AbstractSelect list, String windowName, ValidateListener listener,
            EdsApplicationController controller) {
        super(windowName);
        this.wording = wording;
        this.list = list;
        this.listener = listener;
        this.controller = controller;

        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store wording details.
     */
    private EdsWording wording;
    /**
     * Variable to store list to be updated on validate.
     */
    private AbstractSelect list;
    /**
     * Variable to store validation listener for the pop-up window.
     */
    private ValidateListener listener;
    /**
     * Variable to store EDS Application controller object.
     */
    private EdsApplicationController controller;

    /**
     * This method is used to initialize this class.
     * <ul>
     * It performs the following operations:
     * <li>Create Edit Wording pop-up.</li>
     * <li>Add labels and text areas for rename(English and French).</li>
     * <li>Add buttons and button listeners for validation.</li>
     * </ul>
     */
    private void init() {
        VerticalLayout mainLayout = (VerticalLayout) getContent();
        mainLayout.setMargin(true);

        GridLayout contentLayout = new GridLayout(2, 3);
        contentLayout.setSpacing(true);

        final TextArea vTAnameFrValue = new TextArea();
        vTAnameFrValue.setRows(5);
        vTAnameFrValue.setValue(wording.getValueByLocale(Locale.FRENCH));
        vTAnameFrValue.setRequired(true);
        vTAnameFrValue.setRequiredError(controller.getBundle().getString("eds-wording-french-name-message"));
        contentLayout.addComponent(new Label(controller.getBundle().getString("eds-wording-french-name-title")));

        contentLayout.addComponent(vTAnameFrValue, 1, 0);

        final TextArea vTAnameEnValue = new TextArea();
        vTAnameEnValue.setRows(5);
        vTAnameEnValue.setValue(wording.getValueByLocale(Locale.ENGLISH));
        vTAnameEnValue.setRequired(true);
        vTAnameEnValue.setRequiredError(controller.getBundle().getString("eds-wording-english-name-message"));
        contentLayout.addComponent(new Label(controller.getBundle().getString("eds-wording-english-name-title")));
        contentLayout.addComponent(vTAnameEnValue, 1, 1);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);

        Button okButton = new Button(controller.getBundle().getString("Admin-proj-validate-button"));
        okButton.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (isValid()) {
                    String formattedValue = Locale.FRENCH.getISO3Language() + ":" + vTAnameFrValue.getValue() + ";"
                            + Locale.ENGLISH.getISO3Language() + ":" + vTAnameEnValue.getValue();
                    wording.setWValue(formattedValue);
                    list.setItemCaption(wording, wording.getCaption());
                    close();

                    if (listener != null) {
                        listener.onValidate(wording);
                    }
                } else {
                    showNotification(controller.getBundle().getString("eds-field-error"));
                }
            }

            private boolean isValid() {
                boolean isValid = true;
                if (!vTAnameFrValue.isValid() || !vTAnameEnValue.isValid()) {
                    isValid = false;
                }
                return isValid;
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

        contentLayout.addComponent(buttonsLayout, 1, 2);
        contentLayout.setComponentAlignment(okButton, Alignment.TOP_RIGHT);

        mainLayout.addComponent(contentLayout);
        mainLayout.setExpandRatio(contentLayout, 1);
        mainLayout.setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setSizeUndefined();

        setResizable(false);
    }

    /**
     * This class is used to validate the text box on the Edit Wording pop-up.
     * 
     * @author Geometric Ltd.
     */
    public static interface ValidateListener {
        /**
         * Abstract method to validate the new wording.
         * 
         * @param wording New wording to be validated.
         */
        public void onValidate(EdsWording wording);
    }
}
