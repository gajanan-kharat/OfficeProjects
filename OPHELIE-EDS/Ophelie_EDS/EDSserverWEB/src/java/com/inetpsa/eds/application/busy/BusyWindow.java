package com.inetpsa.eds.application.busy;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.I_ViewRefreshable;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This class acts as window for an application which is in Maintenance mode
 * 
 * @author Geometric Ltd.
 */
public class BusyWindow extends A_EdsWindow implements I_ViewRefreshable {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public BusyWindow(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold EdsApplicationController value
     */
    private EdsApplicationController controller;

    /**
     * Constant to Define icon for EDS_ICON
     */
    private static final Resource EDS_ICON = ResourceManager.getInstance().getThemeIconResource("icons/eds-logo-large.png");

    /**
     * Initialize window for an application which is in Maintenance mode
     */
    private void init() {

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();

        VerticalLayout displayLayout = new VerticalLayout();
        displayLayout.setSizeFull();
        displayLayout.setStyleName("main-layout");

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSpacing(true);

        mainLayout.addComponent(new Embedded(null, EDS_ICON));

        StringBuilder contentBuilder = new StringBuilder();

        contentBuilder.append("<div style='margin:20px; background-color:ghostwhite;color:blue;'>Application arrêtée pour maintenance.</div>");
        contentBuilder.append("<div style='text-align:center; font-size: 15px; top: 100px;'>");
        contentBuilder.append("Bonjour,<br />");
        contentBuilder.append("L'application Ophelie EDS est en <strong style='color: red;'>maintenance</strong> ...<br />");
        contentBuilder.append("Veuillez réessayer plus tard.<br /><br />");
        contentBuilder.append("Merci de votre compréhension.<br />");
        contentBuilder.append("DRD/DSEE/MOST/OS2E.");
        contentBuilder.append("</div>");

        Label vLBusy = new Label(contentBuilder.toString(), Label.CONTENT_XHTML);

        mainLayout.addComponent(vLBusy);

        displayLayout.addComponent(mainLayout);
        displayLayout.setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);
        displayLayout.setExpandRatio(mainLayout, 1f);
        displayLayout.addComponent(controller.getFooter());

        contentLayout.addComponent(controller.getHeader());
        contentLayout.addComponent(displayLayout);
        contentLayout.setExpandRatio(displayLayout, 1f);
        setContent(contentLayout);
    }

    /**
     * This method refresh the view
     */
    public void refreshView() {
    }
}
