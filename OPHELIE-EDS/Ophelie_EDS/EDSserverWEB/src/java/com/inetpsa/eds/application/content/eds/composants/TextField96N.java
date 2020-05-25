package com.inetpsa.eds.application.content.eds.composants;

import com.vaadin.ui.TextField;

/**
 * This class provide TextField96N component
 * 
 * @author Geometric Ltd.
 */
public class TextField96N extends TextField {
    // PUBLIC
    /**
     * Default constructor
     */
    public TextField96N() {
        init();
    }

    /**
     * Method returns unique identifier
     * 
     * @return unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Method set unique identifier
     * 
     * @param id unique identifier to set
     */
    public void setId(String id) {
        this.id = id;
    }

    // PROTECTED

    // PRIVATE
    /**
     * Variable to hold value of String for unique identifier
     */
    private String id;

    /**
     * Initialize TextField96N
     */
    private void init() {
    }
}
