//COPYRIGHT PSA Peugeot Citroen 2014
/**********************************************************************************************************
 *
 * FILE NAME	  : FlagButton.java
 *
 * SOCIETE        : PSA
 * PROJET         : EDS B2B Evolutions
 * VERSION        : V1.0
 * DATE           : 02/04/2012
 *
 * AUTEUR         : GEOMETRIC LTD.
 *
 * DESCRIPTION    : This button display flag buttons.
 *					
 **********************************************************************************************************/
package com.inetpsa.eds.application.header;

import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.ui.NativeButton;

/**
 * This button display flag buttons.
 * 
 * @author Geometric Ltd.
 */
public class FlagButton extends NativeButton {

    /**
     * Variable to hold EDS Application controller value
     */
    private EdsApplicationController controller;

    /**
     * Public parameterized constructor
     * 
     * @param listener Mouse Click listener
     */
    public FlagButton(ClickListener listener) {
        init();
        addListener(listener);

    }

    /**
     * Initialize flag button
     */
    private void init() {

    }

}
