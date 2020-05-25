package com.inetpsa.eds.application.content.eds.composants;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.EDSTools;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

/**
 * This class provide TwinTextField component
 * 
 * @author Geometric Ltd.
 */
public class TwinTextField extends GridLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param twin Check if Twin text field
     * @param controller Controller of EDS application
     */
    public TwinTextField(boolean twin, EdsApplicationController controller) {
        this.twin = twin;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of TextField for current text
     */
    private TextField courantTXT;
    /**
     * Variable to hold value of TextField for time
     */
    private TextField timeTXT;
    /**
     * Variable to hold value if twin text field
     */
    private boolean twin;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize TwinTextField
     */
    private void init() {

        setMargin(true);
        setColumns(2);
        setRows(2);
        setWidth("100%");
        setColumnExpandRatio(0, 0f);
        setColumnExpandRatio(1, 1f);

        Label l1 = new Label("i:");
        l1.setWidth("10");
        addComponent(l1, 0, 0);
        setComponentAlignment(l1, Alignment.MIDDLE_LEFT);

        courantTXT = new TextField();
        courantTXT.setNullRepresentation("");
        courantTXT.addValidator(new DoubleValidator(controller.getBundle().getString("eds-number-error")));
        courantTXT.setInputPrompt(controller.getBundle().getString("eds-courant-label"));
        addComponent(courantTXT, 1, 0);

        setComponentAlignment(courantTXT, Alignment.MIDDLE_LEFT);

        courantTXT.addListener(new TextChangeListener() {
            public void textChange(TextChangeEvent event) {
                courantTXT.isValid();
            }
        });

        if (twin) {

            Label l2 = new Label("t:");
            l2.setWidth("10");
            addComponent(l2, 0, 1);
            setComponentAlignment(l2, Alignment.MIDDLE_LEFT);

            timeTXT = new TextField();
            timeTXT.addValidator(new DoubleValidator(controller.getBundle().getString("eds-number-error")));
            timeTXT.setNullRepresentation("");
            timeTXT.setInputPrompt(controller.getBundle().getString("eds-time-label"));
            addComponent(timeTXT, 1, 1);

            setComponentAlignment(timeTXT, Alignment.MIDDLE_LEFT);

            timeTXT.addListener(new TextChangeListener() {
                public void textChange(TextChangeEvent event) {
                    timeTXT.isValid();
                }
            });

        }

    }

    /**
     * Method returns the current text
     * 
     * @return current text in float format
     */
    public Float getCourant() {
        if (courantTXT.getValue() == null) {
            return null;
        }
        return EDSTools.convertStringToFloat(courantTXT.getValue().toString());
    }

    /**
     * Method returns the time
     * 
     * @return Time in float format
     */
    public Float getTime() {
        if (timeTXT.getValue() == null) {
            return null;
        }
        return EDSTools.convertStringToFloat(timeTXT.getValue().toString());
    }

    /**
     * Method set the current value
     * 
     * @param val Value to be set
     */
    public void setCourant(Float val) {
        if (val == null) {
            courantTXT.setValue("");
        }
        courantTXT.setValue(val);
    }

    /**
     * Method set the time
     * 
     * @param val Time to be set
     */
    public void setTime(Float val) {
        if (val == null) {
            timeTXT.setValue("");
        }
        timeTXT.setValue(val);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractComponentContainer#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        courantTXT.setEnabled(enabled);
        timeTXT.setEnabled(enabled);
    }

    /**
     * This method reset the current text and time value
     */
    public void reset() {
        courantTXT.setValue("");
        timeTXT.setValue("");
    }
}
