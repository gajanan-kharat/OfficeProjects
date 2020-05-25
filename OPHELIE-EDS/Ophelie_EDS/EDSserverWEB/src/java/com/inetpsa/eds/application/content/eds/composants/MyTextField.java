package com.inetpsa.eds.application.content.eds.composants;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.inetpsa.eds.tools.EDSTools;
import com.vaadin.ui.TextField;

/**
 * This class provide MyTextField component
 * 
 * @author Geometric Ltd.
 */
public class MyTextField extends TextField {
    // PUBLIC
    /**
     * Variable to hold value of NumberFormat
     */
    private NumberFormat formatter = new DecimalFormat("#0.00");

    /**
     * Default Constructor
     */
    public MyTextField() {
        init();
    }

    /**
     * Constructor with caption
     * 
     * @param caption
     */
    public MyTextField(String caption) {
        super(caption);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize MyTextField component
     */
    private void init() {
        setNullRepresentation("");
    }

    /**
     * This method returns value of Text Field
     * 
     * @return Value of Text Field
     */
    public String getString() {
        if (super.getValue() == null) {
            return "";
        }
        return "" + super.getValue();
    }

    /**
     * This method returns Float value of Text Field
     * 
     * @return Float value of Text field
     */
    public Float getFloat() {
        if (super.getValue() == null || super.getValue() == "") {
            return null;
        }
        return EDSTools.convertStringToFloat(super.getValue().toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractTextField#setValue(java.lang.Object)
     */
    @Override
    public void setValue(Object value) throws ReadOnlyException, ConversionException {
        if (value == null) {
            super.setValue("");
        } else {
            super.setValue(value);
        }
    }

    /**
     * This method check if value is float
     * 
     * @return Check if value is float
     */
    public boolean isFloat() {
        if (super.getValue() == null || super.getValue() == "") {
            return true;
        }
        try {
            Float.parseFloat(getString());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * This method returns Integer value of Text Field
     * 
     * @return Integer value of Text field
     */
    public Integer getInteger() {
        if (super.getValue() == null || super.getValue() == "") {
            return 0;
        }
        return Integer.parseInt(super.getValue().toString());
    }
}
