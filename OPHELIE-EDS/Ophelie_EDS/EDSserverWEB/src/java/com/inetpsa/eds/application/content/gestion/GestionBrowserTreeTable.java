package com.inetpsa.eds.application.content.gestion;

import java.util.Date;
import java.util.Map;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.GestionConnector;
import com.inetpsa.eds.dao.GestionConnectorException;
import com.inetpsa.gestion.local.model.L_SUser;
import com.inetpsa.gestion.sync.dao.GestionDAO;
import com.inetpsa.gestion.sync.exceptions.E_Auth;
import com.inetpsa.gestion.sync.exceptions.E_Forbidden;
import com.inetpsa.gestion.sync.exceptions.E_NoElement;
import com.inetpsa.gestion.sync.exceptions.E_NoRight;
import com.inetpsa.gestion.sync.model.A_NodeFS;
import com.inetpsa.gestion.sync.model.BSXNodeFS;
import com.inetpsa.gestion.sync.model.ContainerFS;
import com.inetpsa.gestion.sync.model.DirectoryFS;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.TreeTable;

/**
 * Gestion browser panel. This browser panel extends a vaadin's tree table view to displays its informations. The 'refresh' method should be manually
 * called at least one time to update the view according to the Gestion server content. During this operation any error related to Gestion server
 * could happen. During its construction, this class will also try to retrieve a Gestion connector instance, resulting in an error if the Gestion
 * server connection failed for any reason.
 * 
 * @author Guillaume VILLEREZ - Alter Frame
 */
public class GestionBrowserTreeTable extends TreeTable {
    // PUBLIC
    /**
     * Gestion browser constructor. By default, the browser will show "/" and select no container.
     * 
     * @param edsController The EDS application controller.
     * @param typeContents A list of the types of containers to display in the browser, with IDs and names of the types.
     * @throws GestionConnectorException Thrown of the Gestion connector creation/retrieving failed.
     */
    public GestionBrowserTreeTable(EdsApplicationController edsController, Map<Integer, String> typeContents) throws GestionConnectorException {
        this(edsController, typeContents, "/");
    }

    /**
     * Gestion browser constructor with custom root directory.By default, the browser will select no container.
     * 
     * @param edsController The EDS application controller.
     * @param root The root directory to display.
     * @param typeContents A list of the types of containers to display in the browser, with IDs and names of the types.
     * @throws GestionConnectorException Thrown of the Gestion connector creation/retrieving failed.
     */
    public GestionBrowserTreeTable(EdsApplicationController edsController, String root, Map<Integer, String> typeContents)
            throws GestionConnectorException {
        this(edsController, root, typeContents, null);
    }

    /**
     * Gestion browser constructor with custom selected container.By default, the browser will show "/" as the root directory.
     * 
     * @param edsController The EDS application controller.
     * @param typeContents A list of the types of containers to display in the browser, with IDs and names of the types.
     * @param selectedID The ID of the element to select by default.
     * @throws GestionConnectorException Thrown of the Gestion connector creation/retrieving failed.
     */
    public GestionBrowserTreeTable(EdsApplicationController edsController, Map<Integer, String> typeContents, String selectedID)
            throws GestionConnectorException {
        this(edsController, "/", typeContents, selectedID);
    }

    /**
     * Gestion browser constructor with custom selected container.
     * 
     * @param edsController The EDS application controller.
     * @param root The root directory to display.
     * @param typeContents A list of the types of containers to display in the browser.
     * @param selectedID The ID of the element to select by default.
     * @throws GestionConnectorException Thrown of the Gestion connector creation/retrieving failed.
     */
    public GestionBrowserTreeTable(EdsApplicationController edsController, String root, Map<Integer, String> typeContents, String selectedID)
            throws GestionConnectorException {
        this.edsController = edsController;

        init();

        // Initialize the controller, and wire its listener to ours.
        this.controller = new AbstractGestionBrowserController(GestionConnector.getDao(), root, typeContents, selectedID) {

            @Override
            protected void setSelectedItem(A_NodeFS selectedNode) {
                select(selectedNode);
                setTreeItemVisible(selectedNode, true);
            }

            @Override
            protected void addNodeDirectory(Resource resource, DirectoryFS directory, DirectoryFS parent) {

                if (directory.getID().equals(GestionDAO.DIRECTORY_ROOT))
                    return;

                addItem(new Object[] { directory.getName(), "", formatUser(directory.getCreationUser()), directory.getCreationDate(),
                        formatUser(directory.getModificationUser()), directory.getModificationDate(), null, formatUser(directory.getDeletedUser()),
                        directory.getDeletedDate(), null }, directory);
                setItemIcon(directory, resource);

                if (!parent.getID().equals(GestionDAO.DIRECTORY_ROOT)) {
                    setParent(directory, parent);
                }
            }

            @Override
            protected void addNodeContainer(Resource resource, ContainerFS container, DirectoryFS parent) {

                addItem(new Object[] { container.getName(), "", formatUser(container.getCreationUser()), container.getCreationDate(),
                        formatUser(container.getModificationUser()), container.getModificationDate(), formatUser(container.getLockUser()),
                        formatUser(container.getDeletedUser()), container.getDeletedDate(),
                        container.isCompleteLocal() || container.isCompleteServer() ? "Oui" : "Non" }, container);
                setItemIcon(container, resource);
                setChildrenAllowed(container, false);

                if (!parent.getID().equals(GestionDAO.DIRECTORY_ROOT)) {
                    setParent(container, parent);
                }
            }

            @Override
            protected void addNodeBSXBox(Resource resource, BSXNodeFS bsxContainer, ContainerFS parent) {

            }
        };

        addListener(this.controller);
    }

    /**
     * Force the refresh of the view. This action will fetch the Gestion server to look for any changes. This method needs to be called at least one
     * time after the tree table view creation to crate the view's content. Any error thrown here directly come from the Gestion DAO during the
     * directories retrieving.
     * 
     * @throws E_NoRight Thrown if the account used doesn't have sufficient credentials.
     * @throws E_Auth Thrown if an authentication error occurred.
     * @throws E_NoElement Thrown if the root container doesn't exists.
     * @throws E_Forbidden thrown if the account used doesn't have sufficient credentials.
     */
    public void refresh() throws E_NoRight, E_Auth, E_NoElement, E_Forbidden {
        this.controller.refresh();
    }

    /**
     * Set Icon for the specified types.
     * 
     * @param icons The icons, per container type.
     */
    public void setIcons(Map<Integer, ThemeResource> icons) {
        this.controller.setIcons(icons);
    }

    /**
     * Return the actual selected container. Return null if no container is currently selected.
     * 
     * @return The actual selected container, or null.
     */
    public ContainerFS getSelectedValue() {
        return controller.getSelectedContainer();
    }

    // PROTECTED
    /**
     * To be override if needed. Called on each container selection.
     * 
     * @param container The selected container.
     */
    protected void onSelectedValue(ContainerFS container) {
        // Called when a container is selected.
    }

    // PRIVATE
    /**
     * Browser controller.
     */
    private AbstractGestionBrowserController controller;

    /**
     * EDS application controller.
     */
    private EdsApplicationController edsController;

    /**
     * Initialize the content of the tree table.
     */
    private void init() {
        // Custom style
        this.setStyleName("containers-browser");
        this.setWidth("100%");

        // Set table headers
        this.addContainerProperty(edsController.getBundle().getString("admin-gestion-con-header-rep-container"), String.class, null);
        this.addContainerProperty(edsController.getBundle().getString("admin-gestion-con-header-type"), String.class, null);
        this.addContainerProperty(edsController.getBundle().getString("admin-gestion-con-header-created-by"), String.class, null);
        this.addContainerProperty(edsController.getBundle().getString("admin-gestion-con-header-creation-date"), Date.class, null);
        this.addContainerProperty(edsController.getBundle().getString("admin-gestion-con-header-modified-by"), String.class, null);
        this.addContainerProperty(edsController.getBundle().getString("admin-gestion-con-header-modification-date"), Date.class, null);
        this.addContainerProperty(edsController.getBundle().getString("admin-gestion-con-header-locked-by"), String.class, null);
        this.addContainerProperty(edsController.getBundle().getString("admin-gestion-con-header-deleted-by"), String.class, null);
        this.addContainerProperty(edsController.getBundle().getString("admin-gestion-con-header-deletion-date"), Date.class, null);
        this.addContainerProperty(edsController.getBundle().getString("admin-gestion-con-header-complet"), String.class, null);

        // Set selection options
        this.setImmediate(true);
        this.setSelectable(true);
        this.setMultiSelect(false);
    }

    /**
     * Format the given user to be displayed in the tree table view.
     * 
     * @param user The user to be formatted.
     * @return A string containing the user informations.
     */
    private String formatUser(L_SUser user) {
        if (user == null) {
            return null;
        }

        return user.getFirstname() + " " + user.getLastname() + " (" + user.getLogin() + ")";
    }

    /**
     * Show an item in the tree table view. This will also unfold every folded parent of the element in its hierarchy.
     * 
     * @param itemID The item ID to show.
     * @param visible True if the item should be visible, false otherwise.
     */
    private void setTreeItemVisible(Object itemID, boolean visible) {
        Object parentID = getParent(itemID);

        if (parentID != null) {
            if (visible) {
                setTreeItemVisible(parentID, visible);
                setCollapsed(itemID, false);
            } else {
                setCollapsed(parentID, true);
            }
        } else {
            if (visible) {
                setCollapsed(itemID, false);
            }
        }
    }
}
