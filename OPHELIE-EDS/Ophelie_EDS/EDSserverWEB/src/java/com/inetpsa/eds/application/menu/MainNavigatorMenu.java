package com.inetpsa.eds.application.menu;

import java.util.HashMap;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.menu.dashboard.EN_DB_AbandonedEdsNode;
import com.inetpsa.eds.application.menu.dashboard.EN_DB_AllEdsNode;
import com.inetpsa.eds.application.menu.dashboard.EN_DB_DriftEdsNode;
import com.inetpsa.eds.application.menu.dashboard.EN_DB_LateEdsNode;
import com.inetpsa.eds.application.menu.dashboard.EN_DB_ModifiedEdsNode;
import com.inetpsa.eds.application.menu.dashboard.EN_DB_PendingEdsNode;
import com.inetpsa.eds.application.menu.dashboard.EN_DB_UnmodifiedEdsNode;
import com.inetpsa.eds.application.menu.eds.EN_EM_AffectedEdsNode;
import com.inetpsa.eds.application.menu.eds.EN_EM_AllEdsNode;
import com.inetpsa.eds.application.menu.eds.EN_EM_OwnerEdsNode;
import com.inetpsa.eds.application.menu.eds.EN_EM_SubscribedEdsNode;
import com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode;
import com.inetpsa.eds.application.menu.project.EN_ProjectNode;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsProjectNode;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;

/**
 * This class is used to create Main navigator menu.
 * 
 * @author Geometric Ltd.
 */
public class MainNavigatorMenu extends A_EdsNavigationMenu {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public MainNavigatorMenu(EdsApplicationController controller) {
        super(controller, controller.getBundle().getString("menu-app-princ"));
        init();
    }

    /**
     * The method is used to retrieve the project node of the project ID provided.
     * 
     * @param projectID Project ID for which project node to be provided.
     * @return Project node for given project ID.
     */
    public EN_ProjectNode getProjectNode(String projectID) {
        return projectNodes.get(projectID);
    }

    /**
     * The method is used to select the project node of the project ID provided.
     * 
     * @param projectID Project ID for which project node to be selected.
     */
    public void selectProject(String projectID) {
        if (!navigationTree.isExpanded(projectsContainerNode)) {
            navigationTree.expandItem(projectsContainerNode);
        }
        navigationTree.select(projectNodes.get(projectID));
    }

    /**
     * This method is used to select all the EDS nodes.
     * 
     * @param parameters parameter map for nodes to be selected.
     */
    public void selectAllEdsNode(HashMap<String, String> parameters) {
        unselectNode();

        navigationTree.expandItem(edsMenuNode);
        select(allEdsNode, false);
        allEdsNode.onEnter(parameters);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store list of project details.
     */
    private List<EdsProject> projects;
    /**
     * Variable to store map of all project nodes.
     */
    private HashMap<String, EN_ProjectNode> projectNodes;
    /**
     * Variable to store all projects container node.
     */
    private A_EdsNavigationNode projectsContainerNode;
    /**
     * Variable to store menu(EDS browser) nodes.
     */
    private A_EdsNavigationNode edsMenuNode;
    /**
     * Variable to store dashboard nodes.
     */
    private A_EdsNavigationNode dashboardMenuNode;
    /**
     * Variable to store all the EDS nodes.
     */
    private EN_EM_AllEdsNode allEdsNode;

    /**
     * Initialization method. It is used to load all the projects in the menu. Create the main menu.
     */
    private void init() {
        this.projectNodes = new HashMap<String, EN_ProjectNode>();

        // Loading projects

        this.projects = EDSdao.getInstance().getAllProjects();

        buildProjectMenu();
        buildEdsMenu();
        buildDashBoard();
    }

    /**
     * Construct the project tree
     * 
     * @param parent The parent node
     */
    private void constructProjectTree(A_EdsNavigationNode parentNav, EdsProjectNode parent) {

        Resource projectMenuResource = ResourceManager.getInstance().getThemeIconResource("icons/project-menu.png");

        for (EdsProjectNode node : EDSdao.getInstance().getProjectNodeChild(parent)) {
            A_EdsNavigationNode navNode = new EN_ContainerNode(controller, node.getEdspnName().toString());
            navigationTree.addItem(navNode);
            if (parent != null)
                addNode(navNode, parentNav);
            else
                addNode(navNode, projectsContainerNode);

            // Contained nodes
            constructProjectTree(navNode, node);

            // Contained projects
            for (EdsProject project : EDSdao.getInstance().getAllProjectsInNode(node)) {
                EN_ProjectNode projectNode = new EN_ProjectNode(controller, project);

                addNode(projectNode, navNode);

                navigationTree.setItemIcon(projectNode, projectMenuResource);
                projectNodes.put(project.getPId(), projectNode);
            }
        }
    }

    /**
     * Construct the project tree
     */
    private void constructProjectTree() {
        Resource projectMenuResource = ResourceManager.getInstance().getThemeIconResource("icons/project-menu.png");

        // Contained nodes in root
        constructProjectTree(null, null);

        // Contained projects in root
        for (EdsProject project : EDSdao.getInstance().getAllProjectsInNode(null)) {
            EN_ProjectNode projectNode = new EN_ProjectNode(controller, project);

            addNode(projectNode, projectsContainerNode);

            navigationTree.setItemIcon(projectNode, projectMenuResource);
            projectNodes.put(project.getPId(), projectNode);
        }
    }

    /**
     * This method is used to build project menu navigation tree.
     */
    private void buildProjectMenu() {
        if (controller.userHasSufficientRights(EdsRight.APP_GENERAL_ACCESS_PROJECT)) {

            // Construct tree
            projectsContainerNode = new EN_ContainerNode(controller, controller.getBundle().getString("menu-app-projet"));
            addNode(projectsContainerNode);

            constructProjectTree();

            navigationTree.expandItemsRecursively(projectsContainerNode);
        }
    }

    /**
     * This method is used to build EDS menu navigation tree.
     */
    private void buildEdsMenu() {
        if (controller.userHasSufficientRights(EdsRight.APP_GENERAL_ACCESS_EDS)) {
            edsMenuNode = new EN_ContainerNode(controller, controller.getBundle().getString("menu-app-EDS"));
            addNode(edsMenuNode);

            allEdsNode = new EN_EM_AllEdsNode(controller);
            addNode(new EN_EM_OwnerEdsNode(controller), edsMenuNode);
            addNode(new EN_EM_SubscribedEdsNode(controller), edsMenuNode);
            addNode(new EN_EM_AffectedEdsNode(controller), edsMenuNode);
            addNode(allEdsNode, edsMenuNode);

            Resource edsMenuResource = ResourceManager.getInstance().getThemeIconResource("icons/eds-menu.png");
            for (Object menuNode : navigationTree.getChildren(edsMenuNode)) {
                navigationTree.setItemIcon(menuNode, edsMenuResource);
            }
            // navigationTree.expandItem( edsMenuNode );
        }
    }

    /**
     * This method is used to build dashboard menu.
     */
    private void buildDashBoard() {
        if (controller.userHasSufficientRights(EdsRight.APP_GENERAL_ACCESS_DASHBOARD)) {
            dashboardMenuNode = new EN_ContainerNode(controller, controller.getBundle().getString("menu-app-TBD"));
            addNode(dashboardMenuNode);

            addNode(new EN_DB_PendingEdsNode(controller), dashboardMenuNode);
            addNode(new EN_DB_LateEdsNode(controller), dashboardMenuNode);
            addNode(new EN_DB_DriftEdsNode(controller), dashboardMenuNode);
            addNode(new EN_DB_AbandonedEdsNode(controller), dashboardMenuNode);
            addNode(new EN_DB_ModifiedEdsNode(controller), dashboardMenuNode);
            addNode(new EN_DB_UnmodifiedEdsNode(controller), dashboardMenuNode);
            addNode(new EN_DB_AllEdsNode(controller), dashboardMenuNode);

            Resource dashboardIcon = ResourceManager.getInstance().getThemeIconResource("icons/dashboard-menu.png");
            for (Object menuNode : navigationTree.getChildren(dashboardMenuNode)) {
                navigationTree.setItemIcon(menuNode, dashboardIcon);
            }
        }
    }
}
