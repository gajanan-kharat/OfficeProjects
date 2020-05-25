package com.inetpsa.eds.application.menu;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.admin.access.gestion.EdsGestionDatabaseManagementForm;
import com.inetpsa.eds.application.content.admin.access.perimeter.EdsPerimeterManagementForm;
import com.inetpsa.eds.application.content.admin.access.role.EdsRoleManagementForm;
import com.inetpsa.eds.application.content.admin.access.supplier.EdsSupplierManagementForm;
import com.inetpsa.eds.application.content.admin.componenttype.EdsComponentTypeManagementForm;
import com.inetpsa.eds.application.content.admin.project.administration.EdsProjectAdministrationForm;
import com.inetpsa.eds.application.content.admin.project.date.EdsProjectsDateAffectationForm;
import com.inetpsa.eds.application.content.admin.project.milestones.EdsMilestonesManagementForm;
import com.inetpsa.eds.application.content.admin.project.perimeter.EdsProjectsPerimeterAffectationForm;
import com.inetpsa.eds.application.content.admin.right.application.EdsApplicationRightManagementForm;
import com.inetpsa.eds.application.content.admin.right.form.EdsFormRightManagementForm;
import com.inetpsa.eds.application.content.admin.user.EdsUserManagementForm;
import com.inetpsa.eds.application.content.admin.wording.EdsWordingManagementForm;
import com.inetpsa.eds.application.menu.admin.EN_AdminNode;
import com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsUser;

/**
 * This class is used to create the Navigation menu for Administration.
 * 
 * @author Geometric Ltd.
 */
public class EdsAdminNavigationMenu extends A_EdsNavigationMenu {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EdsAdminNavigationMenu(EdsApplicationController controller) {
        super(controller, "Administration");
        init();
    }

    /**
     * This method is used to select project management node in the navigation tree.
     */
    public void selectProjectManagement() {
        navigationTree.select(projectAdministrationNode);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store 'Project administration' node.
     */
    private EN_AdminNode projectAdministrationNode;
    /**
     * Variable to store 'Milestone management' node.
     */
    private EN_AdminNode milestoneManagementNode;
    /**
     * Variable to store 'Milestone date management' node.
     */
    private EN_AdminNode milestoneDateAffectationNode;
    /**
     * Variable to store 'Project partner management' node.
     */
    private EN_AdminNode projectPerimeterAffectationNode;
    /**
     * Variable to store 'Partner access management' node.
     */
    private EN_AdminNode perimeterManagementNode;
    /**
     * Variable to store 'Supplier access management' node.
     */
    private EN_AdminNode supplierManagementNode;
    /**
     * Variable to store 'Profile management' node.
     */
    private EN_AdminNode roleManagementNode;
    /**
     * Variable to store 'Application rights management' node.
     */
    private EN_AdminNode applicationRightManagementNode;
    /**
     * Variable to store 'Form rights management' node.
     */
    private EN_AdminNode formRightManagementNode;
    /**
     * Variable to store 'Wording management' node.
     */
    private EN_AdminNode wordingManagementNode;
    /**
     * Variable to store 'User management' node.
     */
    private EN_AdminNode userManagementNode;
    /**
     * Variable to store 'EDS model management' node.
     */
    private EN_AdminNode componentTypeManagementNode;
    /**
     * Variable to store 'Query management' node.
     */
    private EN_AdminNode queryManage;
    private EN_AdminNode gestionDatabaseManagementNode;

    /**
     * Initialization method.
     */
    private void init() {
        this.setMargin(true);
        this.setSpacing(true);

        buildAdminMenu();
    }

    /**
     * This method is used to build the navigation tree based on the access the logged in user has.
     */
    private void buildAdminMenu() {

        EdsUser user = controller.getAuthenticatedUser();

        // Project Management
        if (user.getEdsRole().appRightsContains(EdsRight.APP_GENERAL_ACCESS_ADMIN_PROJECT)) {
            A_EdsNavigationNode edsProjectManagementNode = new EN_ContainerNode(controller, controller.getBundle().getString("Admin-proj-title"));
            addNode(edsProjectManagementNode);

            projectAdministrationNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-proj-ss-title-adm"),
                    new EdsProjectAdministrationForm(controller));
            addNode(projectAdministrationNode, edsProjectManagementNode);

            milestoneManagementNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-proj-ss-title-jal"),
                    new EdsMilestonesManagementForm(controller));
            addNode(milestoneManagementNode, edsProjectManagementNode);

            milestoneDateAffectationNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-proj-ss-title-date"),
                    new EdsProjectsDateAffectationForm(controller));
            addNode(milestoneDateAffectationNode, edsProjectManagementNode);

            projectPerimeterAffectationNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-proj-partners-aut"),
                    new EdsProjectsPerimeterAffectationForm(controller));
            addNode(projectPerimeterAffectationNode, edsProjectManagementNode);
        }

        // Access management
        if (user.getEdsRole().appRightsContains(EdsRight.APP_GENERAL_ACCESS_ADMIN_ACCESS)) {
            EN_ContainerNode accessManagementNode = new EN_ContainerNode(controller, controller.getBundle().getString("Admin-acc-title"));
            addNode(accessManagementNode);

            perimeterManagementNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-acc-partn-title"),
                    new EdsPerimeterManagementForm(controller));
            addNode(perimeterManagementNode, accessManagementNode);

            supplierManagementNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-acc-fnr-title"),
                    new EdsSupplierManagementForm(controller));

            addNode(supplierManagementNode, accessManagementNode);

            roleManagementNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-gestion-roles"), new EdsRoleManagementForm(
                    controller));

            addNode(roleManagementNode, accessManagementNode);

            // Gestion Database link
            gestionDatabaseManagementNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-gestion-database-link"),
                    new EdsGestionDatabaseManagementForm(controller));

            addNode(gestionDatabaseManagementNode, accessManagementNode);
        }

        // Rights Management
        if (user.getEdsRole().appRightsContains(EdsRight.APP_GENERAL_ACCESS_ADMIN_RIGHTS)) {
            EN_ContainerNode rightManagementNode = new EN_ContainerNode(controller, controller.getBundle().getString("Admin-droit-title"));
            addNode(rightManagementNode);

            applicationRightManagementNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-droit-title-2"),
                    new EdsApplicationRightManagementForm(controller));
            addNode(applicationRightManagementNode, rightManagementNode);

            formRightManagementNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-droit-form-title"),
                    new EdsFormRightManagementForm(controller));
            addNode(formRightManagementNode, rightManagementNode);
        }

        // Label management
        if (user.getEdsRole().appRightsContains(EdsRight.APP_GENERAL_ACCESS_ADMIN_LABEL)) {
            wordingManagementNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-lib-title-menu"),
                    new EdsWordingManagementForm(controller));
            addNode(wordingManagementNode);
        }

        // User Management
        if (user.getEdsRole().appRightsContains(EdsRight.APP_GENERAL_ACCESS_ADMIN_USER)) {
            userManagementNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-user-title"), new EdsUserManagementForm(
                    controller));
            addNode(userManagementNode);
        }

        // EDS model Management
        if (user.getEdsRole().appRightsContains(EdsRight.APP_GENERAL_ACCESS_ADMIN_CARD)) {
            componentTypeManagementNode = new EN_AdminNode(controller, controller.getBundle().getString("Admin-model-title"),
                    new EdsComponentTypeManagementForm(controller));
            addNode(componentTypeManagementNode);
        }

        // queryManage = new EN_AdminNode(controller,
        // "Query",
        // new EdsQueryManagement(controller));
        //
        // addNode(queryManage);
    }
}
