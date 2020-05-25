package com.inetpsa.eds.application.content.eds.composants;

import com.vaadin.ui.TextArea;

/**
 * This class provide MyTextArea component
 * 
 * @author Geometric Ltd.
 */
public class MyTextArea extends TextArea {
    // PUBLIC
    /**
     * Default Constructor
     */
    public MyTextArea() {
        init();
    }

    /**
     * Parameterized Constructor
     * 
     * @param caption Caption of Text area
     */
    public MyTextArea(String caption) {
        setCaption(caption);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize MyTextArea component
     */
    private void init() {
        setNullRepresentation("");
        setWidth("100%");
        setRows(2);

    }

    /**
     * This method set Text
     * 
     * @param text Text to set
     * @throws ReadOnlyException Exception when mofication in read only text occurs
     * @throws ConversionException Exception when Conversion of Text is not allowed
     */
    public void setText(String text) throws ReadOnlyException, ConversionException {
        if (text == null) {
            setValue("");
        }
        setValue(text);
    }

    /**
     * This method returns the Text
     * 
     * @return Text of Text area
     */
    public String getText() {
        if (getValue() == null) {
            return "";
        }
        return getValue().toString();
    }
}
