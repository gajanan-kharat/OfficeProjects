package com.inetpsa.eds.application.header;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.I_ViewRefreshable;
import com.inetpsa.eds.tools.uri.A_FragmentHandler;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Header representing the PSA portal.
 * 
 * @author Geometric Ltd.
 */
public class PsaHeader extends EdsApplicationHeader

{
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public PsaHeader(EdsApplicationController controller) {
        super(controller);
        this.controller = controller;
        init();
    }

    /**
     * This method is used to refresh the header of EDS application.
     * 
     * @see com.inetpsa.eds.application.I_ViewRefreshable#refreshView()
     */
    public void refreshView() {
        super.refreshView();
        vLNKcontact.setCaption(controller.getBundle().getString("contact"));
        // vLNKhome.setCaption( controller.getBundle().getString( "home" ) );
        vLNKhelp.setCaption(controller.getBundle().getString("help"));
        // searchField.setInputPrompt( controller.getBundle().getString( "home-tab-look-for" ) );
    }

    /**
     * This method is used to generate the home screen after login. It validates the access to the and generates the screen accordingly.
     */
    public void toggleUserDisplay() {
        vLNKhome.setVisible(true);

        menu = getMenu();

        menuBarLayout.addComponent(menu);
        menuBarLayout.setComponentAlignment(menu, Alignment.MIDDLE_RIGHT);
        searchField = getSearchField();
        menuBarLayout.addComponent(searchField);
        menuBarLayout.setComponentAlignment(searchField, Alignment.MIDDLE_RIGHT);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS logo.
     */
    private static final Resource EDS_LOGO = ResourceManager.getInstance().getThemeIconResource("icons/eds-logo.png");
    /**
     * Variable to store PSA logo.
     */
    private static final Resource PSA_LOGO = ResourceManager.getInstance().getThemeIconResource("icons/psa-logo.png");
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store header horizontal layout.
     */
    private HorizontalLayout headerLayout;
    /**
     * Variable to store menu bar horizontal layout.
     */
    private HorizontalLayout menuBarLayout;
    /**
     * Variable to store home screen link.
     */
    private Link vLNKhome;
    /**
     * Variable to store help page link.
     */
    private Link vLNKhelp;
    /**
     * Variable to store contact page link.
     */
    private Link vLNKcontact;
    /**
     * Variable to store search field.
     */
    private SearchField searchField;
    /**
     * Variable to store menu
     */
    private MenuBar menu;

    /**
     * Initialization method. This method is used to create the home screen page header.
     */
    private void init() {
        this.setWidth("100%");

        headerLayout = new HorizontalLayout();
        headerLayout.setMargin(false, true, false, true);
        headerLayout.setWidth("100%");
        headerLayout.setHeight("65px");
        headerLayout.addStyleName("header-title");

        Embedded vIMGtitle = new Embedded(null, EDS_LOGO);
        vIMGtitle.setSizeUndefined();
        headerLayout.addComponent(vIMGtitle);
        headerLayout.setComponentAlignment(vIMGtitle, Alignment.MIDDLE_LEFT);

        Link vLNKpsaLogo = new Link(null, new ExternalResource("http://portail.inetpsa.com/"));
        vLNKpsaLogo.setTargetName("_blank");
        vLNKpsaLogo.setIcon(PSA_LOGO);
        headerLayout.addComponent(vLNKpsaLogo);
        headerLayout.setComponentAlignment(vLNKpsaLogo, Alignment.MIDDLE_RIGHT);

        addComponent(headerLayout);

        menuBarLayout = new HorizontalLayout();
        menuBarLayout.setWidth("100%");
        menuBarLayout.setHeight("30px");
        menuBarLayout.setSpacing(true);
        menuBarLayout.setMargin(false, true, false, true);
        menuBarLayout.addStyleName("header-shorcuts");

        vLNKhome = getvLNKhome();
        /*
         * new Link( controller.getBundle().getString( "home" ) , new ExternalResource( "#" + A_FragmentHandler.HOME_FRAGMENT_KEY ) );
         */
        vLNKhome.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/home.png"));
        menuBarLayout.addComponent(vLNKhome);
        menuBarLayout.setComponentAlignment(vLNKhome, Alignment.MIDDLE_LEFT);

        vLNKhelp = getvLNKhelp();
        /*
         * new Link( controller.getBundle().getString( "help" ) , new ExternalResource( "http://web.cfao-cs.inetpsa.com/aev/RES_OUTILS_EDS.html" ) );
         * vLNKhelp.setTargetName( "_blank" );
         */
        vLNKhelp.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/help.png"));
        menuBarLayout.addComponent(vLNKhelp);
        menuBarLayout.setComponentAlignment(vLNKhelp, Alignment.MIDDLE_LEFT);

        vLNKcontact = getvLNKcontact();
        /*
         * = new Link( controller.getBundle().getString( "contact" ) , new ExternalResource( "http://toolsup-ee.inetpsa.com/" ) );
         * vLNKcontact.setTargetName( "_blank" );
         */
        vLNKcontact.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/contact.png"));

        menuBarLayout.addComponent(vLNKcontact);
        menuBarLayout.setComponentAlignment(vLNKcontact, Alignment.MIDDLE_LEFT);
        menuBarLayout.setExpandRatio(vLNKcontact, 1f);

        addComponent(menuBarLayout);

        vLNKhome.setVisible(false);
    }
}
