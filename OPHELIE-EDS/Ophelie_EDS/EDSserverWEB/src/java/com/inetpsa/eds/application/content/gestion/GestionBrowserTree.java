package com.inetpsa.eds.application.content.gestion;

import java.util.Map;

import org.apache.log4j.Logger;

import com.inetpsa.bsxmanager.BSXmanagerController;
import com.inetpsa.bsxmanager.model.BSXBox;
import com.inetpsa.bsxmanager.model.BX_Path;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.GestionConnector;
import com.inetpsa.eds.dao.GestionConnectorException;
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
import com.vaadin.ui.Tree;

/**
 * Gestion browser panel. This browser panel extends a vaadin's tree table view to displays its informations. The 'refresh' method should be manually
 * called at least one time to update the view according to the Gestion server content. During this operation any error related to Gestion server
 * could happen. During its construction, this class will also try to retrieve a Gestion connector instance, resulting in an error if the Gestion
 * server connection failed for any reason.
 * 
 * @author Guillaume VILLEREZ - Alter Frame
 */
public class GestionBrowserTree extends Tree {

    private static Logger logger = Logger.getLogger(GestionBrowserTree.class);

    // PUBLIC
    /**
     * Gestion browser constructor. By default, the browser will show "/" and select no container.
     * 
     * @param edsController The EDS application controller.
     * @param typeContents A list of the types of containers to display in the browser, with IDs and names of the types.
     * @throws GestionConnectorException Thrown of the Gestion connector creation/retrieving failed.
     */
    public GestionBrowserTree(EdsApplicationController edsController, Map<Integer, String> typeContents) throws GestionConnectorException {
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
    public GestionBrowserTree(EdsApplicationController edsController, String root, Map<Integer, String> typeContents)
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
    public GestionBrowserTree(EdsApplicationController edsController, Map<Integer, String> typeContents, String selectedID)
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
    public GestionBrowserTree(EdsApplicationController edsController, String root, Map<Integer, String> typeContents, String selectedID)
            throws GestionConnectorException {
        this.edsController = edsController;

        init();

        // Initialize the controller, and wire its listener to ours.
        this.controller = new AbstractGestionBrowserController(GestionConnector.getDao(), root, typeContents, selectedID) {

            @Override
            protected void setSelectedItem(A_NodeFS selectedNode) {
                select(selectedNode);
                setTreeItemVisible(selectedNode, true);

                if (selectedNode instanceof ContainerFS)
                    onSelectedValue((ContainerFS) selectedNode);
                else if (selectedNode instanceof BSXNodeFS)
                    onSelectedValue((BSXNodeFS) selectedNode);
            }

            @Override
            protected void addNodeDirectory(Resource resource, DirectoryFS directory, DirectoryFS parent) {

                if (directory.getID().equals(GestionDAO.DIRECTORY_ROOT))
                    return;

                addItem(directory);
                setItemCaption(directory, directory.getName());
                setItemIcon(directory, resource);
                setChildrenAllowed(directory, true);

                if (!parent.getID().equals(GestionDAO.DIRECTORY_ROOT)) {
                    setParent(directory, parent);
                }
            }

            @Override
            protected void addNodeContainer(Resource resource, ContainerFS container, DirectoryFS parent) {

                addItem(container);
                setItemCaption(container, container.getName());
                setItemIcon(container, resource);
                setChildrenAllowed(container, true);

                if (!parent.getID().equals(GestionDAO.DIRECTORY_ROOT)) {
                    setParent(container, parent);
                }
            }

            // GL code addition for CUG-0225
            @Override
            protected void addNodeBSXBox(Resource resource, BSXNodeFS bsxNode, ContainerFS parent) {

                addItem(bsxNode);
                setItemCaption(bsxNode, bsxNode.getName());
                setItemIcon(bsxNode, resource);
                setChildrenAllowed(bsxNode, true);

                if (!parent.getID().equals(GestionDAO.DIRECTORY_ROOT)) {
                    setParent(bsxNode, parent);
                }
            }
        };

        // GL code addition for CUG-0225
        this.addListener(new Tree.ExpandListener() {

            @Override
            public void nodeExpand(ExpandEvent event) {

                if (event.getItemId() instanceof ContainerFS) {

                    ContainerFS container = (ContainerFS) event.getItemId();
                    String containerId = container.getID();
                    try {
                        BSXmanagerController bsXmanagerController = GestionConnector.loadGestionContainer(containerId);

                        if (bsXmanagerController != null) {
                            for (BX_Path bxPath : bsXmanagerController.getListing().getAll()) {
                                String pathName = bxPath.getValue();
                                BSXBox bsxBox = bxPath.getBsxBox();
                                String[] bsxBoxNameArray = pathName.split("/");

                                int bsxBoxNameArrayLength = bsxBoxNameArray.length;
                                String bsxBoxName = bsxBoxNameArray[bsxBoxNameArrayLength - 1];
                                BSXNodeFS bsxNode = new BSXNodeFS(bsxBox.getBsxBoxId(), bsxBoxName, null, null, null, null, null, null, 0, container);
                                bsxNode.setDistribContainerID(bsxBox.getContainerID());
                                controller.addNodeBSXBox(controller.getContainerIcon(container), bsxNode, container);
                            }
                        }

                    } catch (GestionConnectorException ex) {
                        logger.error("Exception occured while adding BSX node", ex);
                    }

                }
            }

        });

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

    protected void onSelectedValue(BSXNodeFS bsxNode) {

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

        // Set selection options
        this.setImmediate(true);
        this.setSelectable(true);
        this.setMultiSelect(false);
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
                expandItem(itemID);
            } else {
                expandItem(parentID);
            }
        } else {
            if (visible) {
                expandItem(itemID);
            }
        }
    }
}
