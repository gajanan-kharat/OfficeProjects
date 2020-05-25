//COPYRIGHT PSA Peugeot Citroen 2014
/**********************************************************************************************************
 *
 * FILE NAME	  : EdsApplicationHeader.java
 *
 * SOCIETE        : PSA
 * PROJET         : EDS B2B Evolutions
 * VERSION        : V1.0
 * DATE           : 02/04/2014
 *
 * AUTEUR         : GEOMETRIC LTD.
 *
 * DESCRIPTION    : This class provide common header elements of EDS application. 
 *					
 **********************************************************************************************************/
package com.inetpsa.eds.application.header;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.I_ViewRefreshable;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.inetpsa.eds.tools.uri.A_FragmentHandler;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide common header elements of EDS application.
 * 
 * @author Geometric Ltd.
 */
public class EdsApplicationHeader extends VerticalLayout implements I_ViewRefreshable {

    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsApplicationHeader(EdsApplicationController controller) {
        super();
        this.controller = controller;

    }

    @Override
    public void refreshView() {
        vLNKhome.setCaption(controller.getBundle().getString("home"));
    }

    /**
     * This method is used to generate the home screen after login. It validates the access to the and generates the screen accordingly.
     */
    public void toggleUserDisplay() {

    }

    /**
     * This method returns link for Help
     * 
     * @return Link for help
     */
    public Link getvLNKhelp() {
        vLNKhelp = new Link(controller.getBundle().getString("help"), new ExternalResource("http://web.cfao-cs.inetpsa.com/aev/RES_OUTILS_EDS.html"));
        vLNKhelp.setTargetName("_blank");

        return vLNKhelp;
    }

    /**
     * This method returns link for contact
     * 
     * @return link for contact
     */
    public Link getvLNKcontact() {
        vLNKcontact = new Link(controller.getBundle().getString("contact"), new ExternalResource("http://toolsup-ee.inetpsa.com/"));
        vLNKcontact.setTargetName("_blank");

        return vLNKcontact;
    }

    /**
     * This method returns link for home
     * 
     * @return link for home
     */
    public Link getvLNKhome() {
        vLNKhome = new Link(controller.getBundle().getString("home"), new ExternalResource("#" + A_FragmentHandler.HOME_FRAGMENT_KEY));

        return vLNKhome;
    }

    /**
     * This method returns SearchField Instance
     * 
     * @return SearchField SearchField Instance
     */
    public SearchField getSearchField() {
        searchField = new SearchField();
        searchField.setImmediate(true);
        searchField.setInputPrompt(controller.getBundle().getString("home-tab-look-for"));
        searchField.addEnterHitListener(new SearchField.EnterHitListener() {
            public void enterHit(String text) {
                try {
                    controller.getUriFragmentUtility().setFragment("search=" + URLEncoder.encode(text.trim(), "UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(PsaHeader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        searchField.addListener(new FocusListener() {
            public void focus(FocusEvent event) {
                controller.getApplication().getMainWindow().addActionHandler(searchField);
            }
        });
        searchField.addListener(new BlurListener() {
            public void blur(BlurEvent event) {
                controller.getApplication().getMainWindow().removeActionHandler(searchField);
            }
        });
        return searchField;
    }

    /**
     * This method returns the Drop down menu.
     * 
     * @return MenuBar Drop down menu
     */
    public MenuBar getMenu() {
        menu = new MenuBar();
        menu.setHeight("8px");
        menu.setSizeUndefined();
        MenuItem parameters = menu.addItem(controller.getAuthenticatedUser().toFullIdentity(),
                ResourceManager.getInstance().getThemeIconResource("icons/arrow-expanded.png"), null);
        parameters.addItem(controller.getBundle().getString("Param-title"), new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                controller.getUriFragmentUtility().setFragment(A_FragmentHandler.USER_PARAMS_FRAGMENT_KEY);
            }
        });
        if (controller.userHasSufficientRights(EdsRight.APP_GENERAL_ACCESS_ADMIN)) {
            parameters.addItem(controller.getBundle().getString("menu-princ-admin"), new MenuBar.Command() {
                public void menuSelected(MenuItem selectedItem) {
                    controller.getNavigationMenu().unselectNodes();
                    controller.getUriFragmentUtility().setFragment(A_FragmentHandler.ADMIN_FRAGMENT_KEY);
                }
            });
        }
        parameters.addItem(controller.getBundle().getString("menu-princ-deconnex"), new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                controller.getNavigationMenu().unselectNodes();
                controller.getApplication().close();
            }
        });

        return menu;
    }

    /**
     * Variable to hold value of EdsApplicationController
     */
    private EdsApplicationController controller;

    /**
     * Variable to store help page link.
     */
    private Link vLNKhelp;
    /**
     * Variable to store contact page link.
     */
    private Link vLNKcontact;
    /**
     * Variable to store home screen link.
     */
    private Link vLNKhome;
    /**
     * Variable to store search field.
     */
    private SearchField searchField;

    /**
     * Variable to store Menu bar
     */
    private MenuBar menu;

}
