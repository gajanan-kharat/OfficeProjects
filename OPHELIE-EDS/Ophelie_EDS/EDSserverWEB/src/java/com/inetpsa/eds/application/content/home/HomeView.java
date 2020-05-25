package com.inetpsa.eds.application.content.home;

import java.text.MessageFormat;
import java.util.ArrayList;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This component is a view of the home page.
 * <ul>
 * The home page displays three summary tables showing:
 * <li>The last n sheets visited,</li>
 * <li>That the last n sheets the user has subscribed,</li>
 * <li>The last n projects consulted, n is a parameter defined in the user configuration.</li>
 * </ul>
 * 
 * @author Geometric Ltd.
 */
public class HomeView extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public HomeView(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method is used to refresh/reload the home page.
     */
    public void refresh() {
        // Retrieving data needed
        EDSdao dao = EDSdao.getInstance();

        ArrayList<EdsEds> lastViewedEdsList = new ArrayList<EdsEds>();
        for (Object edsId : controller.getAuthenticatedUser().getConfig().getLastViewedEdses()) {
            lastViewedEdsList.add(dao.getEdsByID((String) edsId));
        }

        ArrayList<EdsEds> lastSubscribedEdsList = new ArrayList<EdsEds>();
        for (Object edsRef : controller.getAuthenticatedUser().getConfig().getLastSubscribedEdses()) {
            lastSubscribedEdsList.add(dao.getEdsByRef((String) edsRef));
        }

        ArrayList<EdsProject> lastViewedProjectsList = new ArrayList<EdsProject>();
        for (Object projectID : controller.getAuthenticatedUser().getConfig().getLastViewedProjects()) {
            lastViewedProjectsList.add(dao.getProjectbyID((String) projectID));
        }

        // Filling tables
        lastViewedEdsTable.setEdsList(lastViewedEdsList);
        lastSubscribedEdsTable.setEdsList(lastSubscribedEdsList);
        lastViewedProjectsTable.setProjectList(lastViewedProjectsList);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store home screen large icon.
     */
    private static final Resource HOME_ICON = ResourceManager.getInstance().getThemeIconResource("icons/eds-logo-large.png");
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Table to store last viewed EDS.
     */
    private EdsShortTable lastViewedEdsTable;
    /**
     * Table to store last subscribed EDS.
     */
    private EdsShortTable lastSubscribedEdsTable;
    /**
     * Table to store last viewed projects.
     */
    private ProjectShortTable lastViewedProjectsTable;

    /**
     * Initialization method. This method is used to display the home screen.
     */
    private void init() {
        // Initialize the GUI
        this.setMargin(true);
        this.setSpacing(true);
        Label welcomeLabel = new Label(MessageFormat.format(controller.getBundle().getString("home-tab-hello"), controller.getAuthenticatedUser()
                .getUFirstname()));

        welcomeLabel.setStyleName("title");
        addComponent(welcomeLabel);

        Embedded icon = new Embedded(null, HOME_ICON);
        addComponent(icon);
        setComponentAlignment(icon, Alignment.TOP_CENTER);

        HorizontalLayout homeTables = new HorizontalLayout();
        homeTables.setWidth("100%");
        homeTables.setSpacing(true);

        lastViewedEdsTable = new EdsShortTable(controller);
        lastViewedEdsTable.setCaption(MessageFormat.format(controller.getBundle().getString("home-EDS-consult"), controller.getAuthenticatedUser()
                .getConfig().getLastViewedEdsesLimit()));
        lastViewedEdsTable.setPageLength(controller.getAuthenticatedUser().getConfig().getLastViewedEdsesLimit());
        homeTables.addComponent(lastViewedEdsTable);

        lastSubscribedEdsTable = new EdsShortTable(controller);
        lastSubscribedEdsTable.setCaption(MessageFormat.format(controller.getBundle().getString("home-EDS-aband"), controller.getAuthenticatedUser()
                .getConfig().getLastSubscribedEdsesLimit()));
        lastSubscribedEdsTable.setPageLength(controller.getAuthenticatedUser().getConfig().getLastSubscribedEdsesLimit());
        homeTables.addComponent(lastSubscribedEdsTable);

        lastViewedProjectsTable = new ProjectShortTable(controller);
        lastViewedProjectsTable.setCaption(MessageFormat.format(controller.getBundle().getString("home-project-consult"), controller
                .getAuthenticatedUser().getConfig().getLastViewedProjectsLimit()));
        lastViewedProjectsTable.setPageLength(controller.getAuthenticatedUser().getConfig().getLastViewedProjectsLimit());
        homeTables.addComponent(lastViewedProjectsTable);

        lastViewedEdsTable.setWidth("100%");
        lastSubscribedEdsTable.setWidth("100%");
        lastViewedProjectsTable.setWidth("100%");

        // Finalisation
        addComponent(homeTables);
    }
}
