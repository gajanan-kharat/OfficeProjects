/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.application;

import com.inetpsa.eds.dao.EDSdao;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Runo;

/**
 * Main window of the user.
 * 
 * @author Geometric Ltd.
 */
public class EdsUserWindow extends A_EdsWindow {
    /**
     * PUBLIC parameterized constructor
     * 
     * @param controller EDS application controller
     */

    public EdsUserWindow(EdsApplicationController controller) {
        super("Eds | Electrical DataSheet Tool");
        this.controller = controller;
        init();
    }

    /**
     * This method Set User Menu
     */
    public void setUserMenu() {
        splitPanel.replaceComponent(splitPanel.getFirstComponent(), controller.getNavigationMenu());
        splitPanel.setSplitPosition(290, Sizeable.UNITS_PIXELS);
    }

    /**
     * This method Set admin menu
     */

    public void setAdminMenu() {
        splitPanel.replaceComponent(splitPanel.getFirstComponent(), controller.getAdminNavigationMenu());
        splitPanel.setSplitPosition(290, Sizeable.UNITS_PIXELS);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable that holds EdaApplicationController value
     */
    private EdsApplicationController controller;
    /**
     * Variable that holds HorizontalSplitPanel value
     */
    private HorizontalSplitPanel splitPanel;

    /**
     * This method initialize User window
     */
    private void init() {

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();

        controller.getHeader().toggleUserDisplay();
        mainLayout.addComponent(controller.getHeader());

        VerticalLayout centerLayout = new VerticalLayout();
        centerLayout.setStyleName("main-layout");
        centerLayout.setSizeFull();

        splitPanel = new HorizontalSplitPanel();
        splitPanel.setStyleName(Runo.SPLITPANEL_REDUCED);
        splitPanel.setSplitPosition(260, Sizeable.UNITS_PIXELS);
        splitPanel.setSizeFull();
        splitPanel.addComponent(controller.getNavigationMenu());

        VerticalLayout rightLayout = new VerticalLayout();
        rightLayout.addComponent(controller.getActionBar());
        rightLayout.addComponent(controller.getContent());

        rightLayout.setExpandRatio(controller.getContent(), 1);
        splitPanel.addComponent(rightLayout);

        centerLayout.addComponent(splitPanel);
        centerLayout.setExpandRatio(splitPanel, 1f);

        centerLayout.addComponent(controller.getFooter());

        mainLayout.addComponent(centerLayout);
        mainLayout.setExpandRatio(centerLayout, 1);

        setContent(mainLayout);
        addComponent(controller.getUriFragmentUtility());

        // addListener( new Window.CloseListener()
        // {
        // private static final long serialVersionUID = 1L;
        //
        // @Override
        // public void windowClose( CloseEvent e )
        // {

        // e.getWindow();
        // if ( controller.getReadForm() != null )
        // {
        // EDSdao.getInstance().freeFormAccess( controller.getAuthenticatedUser() ,
        // controller.getReadForm().getEds().getEdsId() ,
        // controller.getReadForm().getID() );
        // controller.getApplication().close();
        // }
        //
        // }
        // } );
    }
}
