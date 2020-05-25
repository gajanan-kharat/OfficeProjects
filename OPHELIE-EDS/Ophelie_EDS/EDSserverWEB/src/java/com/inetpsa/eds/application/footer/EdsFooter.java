package com.inetpsa.eds.application.footer;

import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Footer representing the PSA portal.
 * 
 * @author Geometric Ltd.
 */
public class EdsFooter extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EdsFooter(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method is used to remove version label.
     */
    public void removeVersionLabel() {
        if (getComponentCount() == 2) {
            this.removeComponent(vLBLversion);
        }
    }

    /**
     * This method is used to show version label.
     */
    public void showVersionLabel() {
        if (getComponentCount() == 1) {
            this.addComponent(vLBLversion);
            this.setComponentAlignment(vLBLversion, Alignment.TOP_CENTER);
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Label for showing the version.
     */
    private Label vLBLversion;
    /**
     * Variable to store the EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * Initialization method. This method adds the footer to the EDS application page.
     */
    private void init() {
        this.setWidth("100%");
        this.setStyleName("footer");

        Label vLBLcopyright = new Label("Copyright DRD/DSEE/MOST/OS2E pour PSA PEUGEOT CITROËN © 2013");
        vLBLcopyright.setSizeUndefined();
        this.addComponent(vLBLcopyright);
        this.setComponentAlignment(vLBLcopyright, Alignment.TOP_CENTER);

        try {
            ResourceBundle bnd = ResourceBundle.getBundle("com.inetpsa.eds.build");
            vLBLversion = new Label(MessageFormat.format(controller.getBundle().getString("eds-footer-version"), bnd.getString("build.number"),
                    bnd.getString("build.date"), bnd.getString("build.author")));
        } catch (Exception ex) {
            vLBLversion = new Label(controller.getBundle().getString("eds-footer-error"));
            ex.printStackTrace();
        }
        vLBLversion.setSizeUndefined();

        this.addComponent(vLBLversion);
        this.setComponentAlignment(vLBLversion, Alignment.TOP_CENTER);
    }
}
