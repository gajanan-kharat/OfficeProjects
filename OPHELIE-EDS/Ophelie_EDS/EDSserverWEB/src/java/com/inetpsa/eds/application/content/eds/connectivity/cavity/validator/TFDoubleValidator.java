/*
 * Creation : 02/10/2015
 */
package com.inetpsa.eds.application.content.eds.connectivity.cavity.validator;

import com.vaadin.data.Validator;

/**
 * Text Field Double Validator
 * 
 * @author Joao Costa @ Alter Frame
 */
public class TFDoubleValidator implements Validator {

    private boolean allowNegative;

    public TFDoubleValidator(boolean allowNegative) {
        super();
        this.allowNegative = allowNegative;
    }

    @Override
    public void validate(Object value) throws InvalidValueException {
        if (!isValid(value)) {
            throw new InvalidValueException("Invalid Value");
        }
    }

    @Override
    public boolean isValid(Object value) {
        Double number = null;
        boolean valid = true;
        if (value instanceof Double) {
            number = (Double) value;
        } else {
            String text = (String) value;

            if (text != null && !text.isEmpty()) {
                try {
                    number = Double.parseDouble(text);
                } catch (NumberFormatException e) {
                    valid = false;
                }
            }
        }
        if (number == null || (!allowNegative && number < 0)) {
            valid = false;
        }
        return valid;
    }

}
