package com.inetpsa.eds.application.content.eds.cse;

import com.inetpsa.eds.tools.EDSTools;
import com.vaadin.ui.TextField;
import java.util.ArrayList;

/**
 * This class provide component float filed
 * 
 * @author Geometric Ltd.
 */
public class FloatField extends TextField {
    // PUBLIC
    /**
     * Interface acts as listener for format error
     * 
     * @author Geometric Ltd.
     */
    public static interface FormatErrorListener {
        /**
         * This method process NumberFormatException error
         * 
         * @param ex Object of NumberFormatException
         */
        public void processError(NumberFormatException ex);
    }

    /**
     * Default constructor
     */
    public FloatField() {
        init();
    }

    /**
     * This method add FormatErrorListener
     * 
     * @param listener FormatErrorListener object
     */
    public void addFormatErrorListener(FormatErrorListener listener) {
        formatErrorListeners.add(listener);
    }

    /**
     * This method remove FormatErrorListener
     * 
     * @param listener FormatErrorListener object
     */
    public void removeFormatErrorListener(FormatErrorListener listener) {
        formatErrorListeners.remove(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractTextField#getValue()
     */
    @Override
    public Float getValue() {
        if (super.getValue() != null) {
            try {
                floatValue = Float.parseFloat(((String) super.getValue()).replace(',', '.'));
            } catch (NumberFormatException ex) {
                // for ( FormatErrorListener listener : formatErrorListeners )
                // {
                // listener.processError( ex );
                // }
            }
        }

        return floatValue;
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

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Float
     */
    private Float floatValue;
    /**
     * Variable to hold value of list for FormatErrorListener
     */
    private ArrayList<FormatErrorListener> formatErrorListeners;

    /**
     * initialize FloatField component
     */
    private void init() {

        this.formatErrorListeners = new ArrayList<FloatField.FormatErrorListener>();
    }
}
