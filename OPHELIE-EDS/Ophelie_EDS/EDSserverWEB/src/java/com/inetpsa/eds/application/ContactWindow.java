//COPYRIGHT PSA Peugeot Citroen 2014
/**********************************************************************************************************
 *
 * FILE NAME	  : ContactWindow.java
 *
 * SOCIETE        : PSA
 * PROJET         : EDS B2B Evolutions
 * VERSION        : V1.0
 * DATE           : 08/04/2014
 *
 * AUTEUR         : GEOMETRIC LTD.
 *
 * DESCRIPTION    : This class provide window for contact.
 *					
 **********************************************************************************************************/
package com.inetpsa.eds.application;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Window for Contact.
 * 
 * @author Geometric Ltd.
 */
public class ContactWindow extends Window {

    /**
     * Variable to hold value of Custom layout
     */
    private CustomLayout custom;

    /**
     * Default constructor
     */
    public ContactWindow() {

        super("EDS B2B Contact");
        init();
    }

    /**
     * Initialize Contact Window
     */
    private void init() {
        // TODO Auto-generated method stub
        custom = new CustomLayout("B2BContact");
        addComponent(custom);
    }

}
