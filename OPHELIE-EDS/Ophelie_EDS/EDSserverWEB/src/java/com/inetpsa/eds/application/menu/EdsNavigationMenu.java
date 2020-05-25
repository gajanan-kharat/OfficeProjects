package com.inetpsa.eds.application.menu;

import com.inetpsa.eds.application.EdsApplicationController;

import com.inetpsa.eds.application.menu.A_EdsNavigationMenu.MenuNodeSelectedEvent;
import com.inetpsa.eds.application.menu.A_EdsNavigationMenu.MenuNodeSelectedListener;
import com.vaadin.ui.VerticalLayout;

/**
 * This object represents the navigation menu. It is possible to add or remove nodes navigation.
 * 
 * @author Geometric Ltd.
 */
public class EdsNavigationMenu extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EdsNavigationMenu(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method is used to un-select Main and EDS navigation menu.
     */
    public void unselectNodes() {
        mainNavigatorMenu.unselectNode();
        edsNavigatorMenu.unselectNode();
    }

    /**
     * This method returns main navigator menu.
     * 
     * @return Main navigation menu.
     */
    public MainNavigatorMenu getMainNavigatorMenu() {
        return mainNavigatorMenu;
    }

    /**
     * This method returns EDS navigator menu.
     * 
     * @return EDS navigator menu.
     */
    public EdsNavigatorMenu getEdsNavigatorMenu() {
        return edsNavigatorMenu;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store the main navigator menu.
     */
    private MainNavigatorMenu mainNavigatorMenu;
    /**
     * Variable to store the EDS navigator menu.
     */
    private EdsNavigatorMenu edsNavigatorMenu;

    /**
     * Initialization method. This method is used to add both EDS and main navigation menus to the main component.
     */
    private void init() {
        this.setMargin(true);
        this.setSpacing(true);

        this.mainNavigatorMenu = new MainNavigatorMenu(controller);
        this.edsNavigatorMenu = new EdsNavigatorMenu(controller);

        this.mainNavigatorMenu.addMenuNodeSelectedListener(new MenuNodeSelectedListener() {
            public void nodeSelected(MenuNodeSelectedEvent event) {
                edsNavigatorMenu.unselectNode();
            }
        });

        this.edsNavigatorMenu.addMenuNodeSelectedListener(new MenuNodeSelectedListener() {
            public void nodeSelected(MenuNodeSelectedEvent event) {
                mainNavigatorMenu.unselectNode();
            }
        });

        addComponent(mainNavigatorMenu);
        addComponent(edsNavigatorMenu);
    }
}
