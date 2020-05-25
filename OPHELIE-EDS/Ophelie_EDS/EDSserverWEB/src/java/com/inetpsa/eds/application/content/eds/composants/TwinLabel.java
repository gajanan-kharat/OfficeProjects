package com.inetpsa.eds.application.content.eds.composants;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * This class provide TwinLabel component
 * 
 * @author Geometric Ltd.
 */
public class TwinLabel extends GridLayout {
    // PUBLIC
    /**
     * Variable to hold value of label
     */
    private Label vLCourant;
    /**
     * Variable to hold value of label
     */
    private Label vLTemps;

    /**
     * Default constructor
     */
    public TwinLabel() {
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize TwinLabel
     */
    private void init() {
        setMargin(true);
        setColumns(1);
        setRows(2);
        setWidth("100%");

        vLCourant = new Label();
        addComponent(vLCourant, 0, 0);

        vLTemps = new Label();
        addComponent(vLTemps, 0, 1);
    }

    /**
     * Method to set value
     * 
     * @param courant Current float value
     * @param temps Temporary float value
     */
    public void setValue(Float courant, Float temps) {
        vLCourant.setValue(courant);
        vLTemps.setValue(temps);
    }
}
