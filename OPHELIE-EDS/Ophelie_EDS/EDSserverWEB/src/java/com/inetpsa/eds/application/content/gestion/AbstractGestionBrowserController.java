package com.inetpsa.eds.application.content.gestion;

import java.util.ArrayList;
import java.util.Map;

import com.inetpsa.gestion.sync.dao.GestionDAO;
import com.inetpsa.gestion.sync.exceptions.E_Auth;
import com.inetpsa.gestion.sync.exceptions.E_Forbidden;
import com.inetpsa.gestion.sync.exceptions.E_NoElement;
import com.inetpsa.gestion.sync.exceptions.E_NoRight;
import com.inetpsa.gestion.sync.model.A_NodeFS;
import com.inetpsa.gestion.sync.model.BSXNodeFS;
import com.inetpsa.gestion.sync.model.ContainerFS;
import com.inetpsa.gestion.sync.model.DirectoryFS;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

/**
 * Gestion browser controller. The class is used to handle the browsing of the directories and containers of Gestion server. Error thrown can be from
 * the Gestion dao itself (during the retrieving of informations), or during its initialization. This abstract class needs to be specialized to
 * display its nodes, in tree or in a tree table view.
 * 
 * @author Guillaume VILLEREZ - Alter Frame
 */
public abstract class AbstractGestionBrowserController implements Property.ValueChangeListener {

    // PUBLIC
    /**
     * Constructor for the gestion browser controller. By default, the "/" directory is used as root.
     * 
     * @param dao The Gestion DAO used to read from.
     * @param typeContents The list of contents to display, with IDs and type names.
     */
    public AbstractGestionBrowserController(GestionDAO dao, Map<Integer, String> typeContents) {
        this(dao, "/", typeContents, null);
    }

    /**
     * Constructor for the gestion browser controller.
     * 
     * @param dao The Gestion DAO used to read from.
     * @param root The root directory.
     * @param typeContents The list of contents to display, with IDs and type names.
     * @param selectedID The ID of the selected container.
     */
    public AbstractGestionBrowserController(GestionDAO dao, String root, Map<Integer, String> typeContents, String selectedID) {
        this.dao = dao;
        this.root = root;
        this.typeContents = typeContents;
        this.selectedID = selectedID;
    }

    /**
     * Refresh all the values.
     * 
     * @throws E_NoRight Thrown if the account used doesn't have sufficient credentials.
     * @throws E_Auth Thrown if an authentication error occurred.
     * @throws E_NoElement Thrown if the root container doesn't exists.
     * @throws E_Forbidden thrown if the account used doesn't have sufficient credentials.
     */
    public void refresh() throws E_NoRight, E_Auth, E_NoElement, E_Forbidden {
        DirectoryFS root = dao.getListing(this.root, true, true, true, new ArrayList<Integer>(typeContents.keySet()), false, 9999);

        if (root != null) {
            for (A_NodeFS child : root.getChildren()) {
                addRecursively(root, child);
            }
        }

        if (selectedContainer != null)
            setSelectedItem(selectedContainer);
        else if (selectedDirectory != null)
            setSelectedItem(selectedDirectory);
    }

    /**
     * Get the selected container.
     * 
     * @return The selected container, or null.
     */
    public ContainerFS getSelectedContainer() {
        return selectedContainer;
    }

    /**
     * Get the selected directory.
     * 
     * @return The selected directory, or null.
     */
    public DirectoryFS getSelectedDirectory() {
        return selectedDirectory;
    }

    public String getRoot() {
        return root;
    }

    /**
     * Set the custom icons to use.
     * 
     * @param icons The custom icons.
     */
    public void setIcons(Map<Integer, ThemeResource> icons) {
        this.icons = icons;
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        Object selected = event.getProperty().getValue();

        if (selected != null) {
            if (selected instanceof ContainerFS) {
                selectedContainer = (ContainerFS) selected;
                selectedDirectory = null;
                selectedID = selectedContainer.getID();

                setSelectedItem(selectedContainer);
            } else if (selected instanceof DirectoryFS) {
                selectedDirectory = (DirectoryFS) selected;
                selectedContainer = null;
                selectedID = selectedDirectory.getID();

                setSelectedItem(selectedDirectory);
            } else if (selected instanceof BSXNodeFS) {
                selectedBsxNode = (BSXNodeFS) selected;
                selectedDirectory = null;
                selectedContainer = null;
                selectedID = selectedBsxNode.getID();
            }
        } else {
            selectedDirectory = null;
            selectedContainer = null;
        }
    }

    // PROTECTED
    /**
     * Sets the selected item.
     * 
     * @param selectedNode the new selected item
     */
    protected abstract void setSelectedItem(A_NodeFS selectedNode);

    /**
     * Adds the node directory.
     * 
     * @param resource the resource
     * @param directory the directory
     * @param parent the parent
     */
    protected abstract void addNodeDirectory(Resource resource, DirectoryFS directory, DirectoryFS parent);

    /**
     * Adds the node container.
     * 
     * @param resource the resource
     * @param container the container
     * @param parent the parent
     */
    protected abstract void addNodeContainer(Resource resource, ContainerFS container, DirectoryFS parent);

    // GL addition for CUG-225
    protected abstract void addNodeBSXBox(Resource resource, BSXNodeFS bsxContainer, ContainerFS parent);

    // PRIVATE
    /** The Gestion dao. */
    private final GestionDAO dao;

    /** The root directory to display. */
    private final String root;

    /** The list of type of containers to display in the renderer. */
    private final Map<Integer, String> typeContents;

    /** ID of the selected item, if any. */
    private String selectedID;

    /** Selected container. Null if no container is selected. */
    private ContainerFS selectedContainer;

    /** Selected directory. Null if no directory is selected. */
    private DirectoryFS selectedDirectory;

    private BSXNodeFS selectedBsxNode;

    /** Selected directory. Null if no directory is selected. */
    private Map<Integer, ThemeResource> icons;

    // Images for the icons
    // CHECKSTYLE:OFF (Icons)

    private final static ThemeResource resContainer = new ThemeResource("icons/container.png");
    private final static ThemeResource resContainerDeleted = new ThemeResource("icons/container-deleted.png");
    private final static ThemeResource resContainerLocked = new ThemeResource("icons/container-locked.png");
    private final static ThemeResource resContainerDeletedLocked = new ThemeResource("icons/container-deleted-locked.png");
    private final static ThemeResource resDirectory = new ThemeResource("icons/directory.png");
    private final static ThemeResource resDirectoryDeleted = new ThemeResource("icons/directory-deleted.png");

    // CHECKSTYLE:ON

    /**
     * Add recursively a node to the controller.
     * 
     * @param parent The parent node.
     * @param child The node to add.
     */
    private void addRecursively(DirectoryFS parent, A_NodeFS child) {
        if (parent == null || child == null) {
            throw new NullPointerException();
        }

        if (child instanceof ContainerFS) {
            ContainerFS childContainer = (ContainerFS) child;

            addNodeContainer(getContainerIcon(childContainer), childContainer, parent);

            // Update selected element
            if (selectedID != null && child.getID().equals(selectedID))
                selectedContainer = (ContainerFS) child;

        } else if (child instanceof DirectoryFS) {
            DirectoryFS childDirectory = (DirectoryFS) child;
            addNodeDirectory(getDirectoryIcon(childDirectory), childDirectory, parent);

            // Update selected element
            if (selectedID != null && child.getID().equals(selectedID))
                selectedDirectory = (DirectoryFS) child;

            // Update for contained elements
            for (A_NodeFS newChild : childDirectory.getChildren()) {
                addRecursively(childDirectory, newChild);

            }
        }

    }

    /**
     * Return a directory icon.
     * 
     * @param directory The directory to display.
     * @return The directory's icon.
     */
    private Resource getDirectoryIcon(DirectoryFS directory) {
        if (directory == null) {
            return null;
        }

        return directory.getDeletedDate() != null ? resDirectoryDeleted : resDirectory;
    }

    /**
     * Return a container icon.
     * 
     * @param container The container to display.
     * @return The container's icon.
     */
    Resource getContainerIcon(ContainerFS container) {
        if (container == null) {
            return null;
        }

        // Custom icon
        if (icons != null && icons.containsKey(container.getTypeContent())) {
            return icons.get(container.getTypeContent());
        }

        // Regular icon
        if (container.getDeletedDate() != null) {
            return container.getLockDate() != null ? resContainerDeletedLocked : resContainerDeleted;
        }

        return container.getLockDate() != null ? resContainerLocked : resContainer;
    }

    public BSXNodeFS getSelectedBsxNode() {
        return selectedBsxNode;
    }

    public void setSelectedBsxNode(BSXNodeFS selectedBsxNode) {
        this.selectedBsxNode = selectedBsxNode;
    }
}
