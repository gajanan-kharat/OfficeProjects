//COPYRIGHT PSA Peugeot Citroen 2014
/**********************************************************************************************************
 *
 * FILE NAME	  : ContactButton.java
 *
 * SOCIETE        : PSA
 * PROJET         : EDS B2B Evolutions
 * VERSION        : V1.0
 * DATE           : 08/04/2014
 *
 * AUTEUR         : GEOMETRIC LTD.
 *
 * DESCRIPTION    : This class provide Contact button.
 *					
 **********************************************************************************************************/
package com.inetpsa.eds.application.header;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Button.ClickListener;

/**
 * This class represents Contact Button.
 * 
 * @author Geometric Ltd.
 */
public class ContactButton extends NativeButton {

    /**
     * Variable to hold EDS Application controller value
     */
    private EdsApplicationController controller;

    /**
     * Public parameterized constructor
     * 
     * @param ClickListener Listener for click on contact
     */
    public ContactButton(ClickListener listener) {
        init();
        addListener(listener);

    }

    /**
     * Initialize Contact Button
     */
    private void init() {
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/fleche_barre.gif"));
    }
}
