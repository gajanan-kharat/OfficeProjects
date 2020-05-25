//COPYRIGHT PSA Peugeot Citroen 2014
/**********************************************************************************************************
 *
 * FILE NAME	  : LoginErrorWindow.java
 *
 * SOCIETE        : PSA
 * PROJET         : EDS B2B Evolutions
 * VERSION        : V1.0
 * DATE           : 02/04/2014
 *
 * AUTEUR         : GEOMETRIC LTD.
 *
 * DESCRIPTION    : This class display Login Error window.
 *					
 **********************************************************************************************************/
package com.inetpsa.eds.application;

import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * This class display Login Error window.
 * 
 * @author Geometric Ltd.
 */
public class LoginErrorWindow extends Window {

    /**
     * Default Constructor
     */
    public LoginErrorWindow() {
        super("Eds | Login Error");
        init();
    }

    /**
     * Variable to hold value of Custom layout
     */
    private CustomLayout custom;

    /**
     * variable to store float value.
     */
    private static final Float FLO_4 = 4f;

    /**
     * Initialize Login error window
     */
    private void init() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();

        custom = new CustomLayout("B2BHeader");

        Resource psaLogo = ResourceManager.getInstance().getThemeIconResource("icons/logo_psa.gif");
        Embedded imgPsaLogo = new Embedded(null, psaLogo);
        custom.addComponent(imgPsaLogo, "PSALOGO");

        Resource b2bPortal = ResourceManager.getInstance().getThemeIconResource("icons/logo_droite_b2b.gif");
        Embedded imgB2bportal = new Embedded(null, b2bPortal);
        custom.addComponent(imgB2bportal, "B2BPORTAL");

        mainLayout.addComponent(custom);
        mainLayout.setExpandRatio(custom, 1f);

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        contentLayout.addStyleName("B2bheader-shorcuts");

        Embedded vIMGErrortitle = new Embedded(null, ResourceManager.getInstance().getThemeIconResource("icons/AccessDenied_Msg.png"));

        contentLayout.addComponent(vIMGErrortitle);
        contentLayout.setComponentAlignment(vIMGErrortitle, Alignment.TOP_CENTER);

        mainLayout.addComponent(contentLayout);
        mainLayout.setExpandRatio(contentLayout, FLO_4);

        setContent(mainLayout);
    }

}
