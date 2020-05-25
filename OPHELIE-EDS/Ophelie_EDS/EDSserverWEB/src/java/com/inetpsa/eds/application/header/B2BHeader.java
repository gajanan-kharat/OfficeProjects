//COPYRIGHT PSA Peugeot Citroen 2014
/**********************************************************************************************************
 *
 * FILE NAME	  : B2BHeader.java
 *
 * SOCIETE        : PSA
 * PROJET         : EDS B2B Evolutions
 * VERSION        : V1.0
 * DATE           : 02/04/2014
 *
 * AUTEUR         : GEOMETRIC LTD.
 *
 * DESCRIPTION    : This class used to display B2B portal specific header.
 *					
 **********************************************************************************************************/
package com.inetpsa.eds.application.header;

import java.util.Locale;

import com.inetpsa.eds.application.ContactWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.inetpsa.eds.tools.uri.A_FragmentHandler;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;

/**
 * This class used to Display B2B portal header.
 * 
 * @author Geometric Ltd.
 */
public class B2BHeader extends EdsApplicationHeader {

    /**
     * Public parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public B2BHeader(EdsApplicationController controller) {
        super(controller);
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.header.EdsApplicationHeader#refreshView()
     */
    @Override
    public void refreshView() {

        super.refreshView();
        vLNKLogout.setCaption(controller.getBundle().getString("menu-princ-deconnex"));
        vLNKhelp.setCaption(controller.getBundle().getString("On-line-help"));
        getHeaderImages();
        vLNKhelp.setResource(new ExternalResource(controller.getBundle().getString("b2b.help.link")));
        vLNKcontact.setCaption(controller.getBundle().getString("contact"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.header.EdsApplicationHeader#toggleUserDisplay()
     */
    @Override
    public void toggleUserDisplay() {
        vLNKhome.setVisible(true);
        vLNKformation.setVisible(true);
        vLNKLogout.setVisible(true);

        menu = getMenu();
        custom.addComponent(menu, "USER");

        searchField = getSearchField();
        searchField.setWidth("110px");
        custom.addComponent(searchField, "SEARCH");

    }

    /**
     * Variable to hold value of EDS application controller
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of Custom layout
     */
    private CustomLayout custom;
    /**
     * Variable to hold value of Link of Help
     */
    private Link vLNKhelp;
    /**
     * Variable to hold value of Link of Contact
     */
    private Button vLNKcontact;
    /**
     * Variable to hold value of Link of Home
     */
    private Link vLNKhome;
    /**
     * Variable to hold value of link of Logout
     */
    private Link vLNKLogout;
    /**
     * Variable to hold value of SearchField
     */
    private SearchField searchField;
    /**
     * Variable to hold value of Menu bar
     */
    private MenuBar menu;
    /**
     * Variable to hold value of Link of Formation
     */
    private Link vLNKformation;
    /**
     * Variable to hold value of Embedded for PSA logo image
     */
    private Embedded imgPsaLogo;
    /**
     * Variable to hold value of Embedded for B2B portal image
     */
    private Embedded imgB2bportal;

    /**
     * Initialize B2B specific header.
     */
    private void init() {
        this.setWidth("100%");
        // Create the custom layout html file, and set it as a component in
        // the current vaadin layout
        // ..\web\VAADIN\themes\psa\layouts\B2BHeader.html
        custom = new CustomLayout("B2BHeader");
        custom.addStyleName("B2bheader-shorcuts");
        addComponent(custom);

        vLNKhelp = new Link(controller.getBundle().getString("On-line-help"), new ExternalResource(controller.getBundle().getString("b2b.help.link")));
        vLNKhelp.setTargetName("_blank");
        vLNKhelp.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/fleche_barre.gif"));
        custom.addComponent(vLNKhelp, "HELP");

        vLNKcontact = new ContactButton(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

                Window win = new ContactWindow();
                win.setModal(true);
                win.setHeight("90%");
                win.setWidth("90%");
                getApplication().getMainWindow().addWindow(win);

            }
        });
        custom.addComponent(vLNKcontact, "CONTACT");

        vLNKhome = getvLNKhome();
        vLNKhome.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/fleche_barre.gif"));
        vLNKhome.setVisible(false);
        custom.addComponent(vLNKhome, "HOME");

        vLNKformation = new Link(controller.getBundle().getString("formation"), new ExternalResource(
                "https://docinfogroupe.psa-peugeot-citroen.com/ead/doc/ref.01301_13_00991/v.vc/fiche"));
        vLNKformation.setTargetName("_blank");
        vLNKformation.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/fleche_barre.gif"));
        vLNKformation.setVisible(false);
        custom.addComponent(vLNKformation, "FORMATION");

        vLNKLogout = new Link(controller.getBundle().getString("menu-princ-deconnex"), new ExternalResource("#"
                + A_FragmentHandler.LOGOUT_FRAGMENT_KEY));

        vLNKLogout.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/fleche_barre.gif"));
        vLNKLogout.setVisible(false);
        custom.addComponent(vLNKLogout, "LOG OUT");

        HorizontalLayout hImagFlags = new HorizontalLayout();
        hImagFlags.setWidth("100%");
        Button enImg = new FlagButton(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                controller.setLocale(Locale.ENGLISH);
            }
        });
        enImg.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/EnglishFlag.png"));
        enImg.setWidth("56px");
        hImagFlags.addComponent(enImg);
        hImagFlags.setComponentAlignment(enImg, Alignment.TOP_LEFT);

        Button frImg = new FlagButton(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                Locale.setDefault(Locale.FRENCH);
                controller.setLocale(Locale.getDefault());

            }
        });
        frImg.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/FrenchFlag.png"));
        frImg.setWidth("56px");
        hImagFlags.addComponent(frImg);
        hImagFlags.setComponentAlignment(frImg, Alignment.TOP_LEFT);
        // hImagFlags.setExpandRatio(frImg,2);

        custom.addComponent(hImagFlags, "SEARCH");

        Label lDataSheet = new Label(controller.getBundle().getString("datasheet"));
        custom.addComponent(lDataSheet, "DATASHEET");

        getHeaderImages();

    }

    /**
     * This method returns local specific images.
     */
    private void getHeaderImages() {
        Resource PSA_LOGO = ResourceManager.getInstance().getThemeIconResource(controller.getBundle().getString("Psa.logo"));
        imgPsaLogo = new Embedded(null, PSA_LOGO);
        custom.addComponent(imgPsaLogo, "PSALOGO");
        Resource B2B_PORTAL = ResourceManager.getInstance().getThemeIconResource(controller.getBundle().getString("b2b.portal"));
        imgB2bportal = new Embedded(null, B2B_PORTAL);
        custom.addComponent(imgB2bportal, "B2BPORTAL");

    }
}
