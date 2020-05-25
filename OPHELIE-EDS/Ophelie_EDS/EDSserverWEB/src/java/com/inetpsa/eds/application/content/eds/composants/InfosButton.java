package com.inetpsa.eds.application.content.eds.composants;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;
import java.io.File;

/**
 * This class provide InfosButton component
 * 
 * @author Geometric Ltd.
 */
public class InfosButton extends NativeButton implements Button.ClickListener {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public InfosButton(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Embedded for image view
     */
    private Embedded imageView;
    /**
     * Variable to hold value of window
     */
    private Window subwindow;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize InfoButton
     */
    private void init() {
        setStyleName(BaseTheme.BUTTON_LINK);
        setIcon(ResourceManager.getInstance().getThemeIconResource("icons/help.png"));
        addListener((ClickListener) this);

        subwindow = new Window(controller.getBundle().getString("eds-graph-ind"));
        VerticalLayout layout = (VerticalLayout) subwindow.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeUndefined();
        subwindow.setModal(true);
        subwindow.addListener(new Window.CloseListener() {
            public void windowClose(Window.CloseEvent e) {
                getWindow().removeComponent(subwindow);
            }
        });

        Resource rsrc = ResourceManager.getInstance().getThemeIconResource("icons/Conso_organe_consolide.png");
        imageView = new Embedded();
        imageView.setSource(rsrc);

        imageView.setType(Embedded.TYPE_IMAGE);
        subwindow.addComponent(imageView);

    }

    /**
     * Method is Called when a Button is clicked.
     * 
     * @param event An event containing information about the click.
     */
    public void buttonClick(ClickEvent event) {
        getWindow().addWindow(subwindow);
    }
}
