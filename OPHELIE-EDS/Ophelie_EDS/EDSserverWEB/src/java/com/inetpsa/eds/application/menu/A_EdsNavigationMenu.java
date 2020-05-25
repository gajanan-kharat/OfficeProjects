package com.inetpsa.eds.application.menu;

import com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode;
import com.inetpsa.eds.application.EdsApplicationController;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.ExpandEvent;
import com.vaadin.ui.VerticalLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This object represents the navigation menu. It is possible to add or remove navigation nodes.
 * 
 * @author Geometric Ltd.
 */
public class A_EdsNavigationMenu extends VerticalLayout implements Serializable {
    // PUBLIC
    /**
     * This interface is used to listen if the menu node is selected.
     * 
     * @author Geometric Ltd.
     */
    public interface MenuNodeSelectedListener {
        /**
         * This method gives the node which is being selected.
         * 
         * @param event Menu node select event.
         */
        public void nodeSelected(MenuNodeSelectedEvent event);
    }

    /**
     * This interface is used to capture the menu node selected event.
     * 
     * @author Geometric Ltd.
     */
    public class MenuNodeSelectedEvent {
        /**
         * This method is used to capture the selected node.
         * 
         * @param selectedNode Selected navidation node.
         */
        public MenuNodeSelectedEvent(A_EdsNavigationNode selectedNode) {
            this.selectedNode = selectedNode;
        }

        /**
         * This method returns the selected node.
         * 
         * @return Selected node.
         */
        public A_EdsNavigationNode getSelectedNode() {
            return selectedNode;
        }

        /**
         * This method sets the consumed flag to true.
         */
        public void consume() {
            this.consumed = true;
        }

        /**
         * This method returns the value of consumed flag.
         * 
         * @return consumed flag.
         */
        public boolean isConsumed() {
            return consumed;
        }

        /**
         * Variable to store the value of selected node.
         */
        private A_EdsNavigationNode selectedNode;
        /**
         * Variable to store consumed flag. Default value set to false.
         */
        private boolean consumed = false;
    }

    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public A_EdsNavigationMenu(EdsApplicationController controller) {
        this(controller, null);
    }

    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param caption Window title.
     */
    public A_EdsNavigationMenu(EdsApplicationController controller, String caption) {
        this.controller = controller;
        this.caption = caption;
        init();
    }

    /**
     * This method is used to add root node to the navigation tree.
     * 
     * @param node Navigation node.
     */
    public void addNode(A_EdsNavigationNode node) {
        navigationTree.addItem(node);
        navigationTree.setItemCaption(node, node.getCaption());
        navigationTree.setChildrenAllowed(node, node.areChildrenAllowed());
    }

    /**
     * This method is used to un-select the current node.
     */
    public void unselectNode() {
        if (currentNode != null) {
            currentNode.onExit();
            navigationTree.unselect(currentNode);
            currentNode = null;
        }
    }

    /**
     * Adds childNode parentNode. parentNode must be contained in the menu.
     * 
     * @param childNode Child node to be added.
     * @param parentNode Parent node to which child node to be added.
     */
    public void addNode(A_EdsNavigationNode childNode, A_EdsNavigationNode parentNode) {
        addNode(childNode);
        navigationTree.setParent(childNode, parentNode);
    }

    /**
     * This method is used to remove a node from the navigation tree.
     * 
     * @param node Navigation node to be removed.
     */
    public void removeNode(A_EdsNavigationNode node) {
        navigationTree.removeItem(node);
    }

    /**
     * This method is used to remove a node from all the children recursively.
     * 
     * @param node Navigation node to be removed.
     */
    public void removeNodeRecursively(A_EdsNavigationNode node) {
        if (node != null) {
            if (node == currentNode) {
                currentNode = null;
                currentRootNode = null;
            }

            if (node.areChildrenAllowed()) {
                Collection<A_EdsNavigationNode> childNodes = new ArrayList<A_EdsNavigationNode>(
                        (Collection<A_EdsNavigationNode>) navigationTree.getChildren(node));
                for (A_EdsNavigationNode childNode : childNodes) {
                    removeNodeRecursively(childNode);
                }
            }
            removeNode(node);
        }
    }

    /**
     * This method is used to retrieve the parent node of a child node.
     * 
     * @param node Child node for which parent node to be retrieved.
     * @return Parent navigation node.
     */
    public A_EdsNavigationNode getParent(A_EdsNavigationNode node) {
        return (A_EdsNavigationNode) navigationTree.getParent(node);
    }

    /**
     * This method is used to retrieve all the child nodes of a node.
     * 
     * @param node Parent navigation node.
     * @return List of child nodes.
     */
    public Collection<A_EdsNavigationNode> getChildren(A_EdsNavigationNode node) {
        return (Collection<A_EdsNavigationNode>) navigationTree.getChildren(node);
    }

    /**
     * This method is used to retrieve the current node.
     * 
     * @return current navigation node.
     */
    public A_EdsNavigationNode getCurrentNode() {
        return currentNode;
    }

    /**
     * This method is used to set the navigation node.
     * 
     * @param node Navidation node to be added.
     */
    public void setCurrentNode(A_EdsNavigationNode node) {
        this.currentNode = node;
    }

    /**
     * This method is used to check if the item/node is expanded.
     * 
     * @param itemId Item id of the item/node to be checked.
     * @return true if node is expanded, else false.
     */
    public boolean isExpanded(Object itemId) {
        return navigationTree.isExpanded(itemId);
    }

    /**
     * This method is used to expand the item/node provided.
     * 
     * @param itemId Item id of the item/node to be expanded.
     * @return true if node is expanded, else false.
     */
    public boolean expandItem(Object itemId) {
        return navigationTree.expandItem(itemId);
    }

    /**
     * This method is used to select the item/node provided.
     * 
     * @param itemId Item id of the item/node to be selected.
     */
    public void select(Object itemId) {
        navigationTree.select(itemId);
    }

    /**
     * This method is used to select the item/node provided.
     * 
     * @param itemId Item id of the item/node to be selected.
     * @param triggerListeners true if listeners to be triggered, else false.
     */
    public void select(Object itemId, boolean triggerListeners) {
        if (triggerListeners) {
            navigationTree.select(itemId);
        } else {
            navigationTree.removeListener(changeListener);
            navigationTree.select(itemId);
            navigationTree.addListener(changeListener);
        }
    }

    /**
     * This method is used to retrieve the navigation tree.
     * 
     * @return Navigation tree.
     */
    public Tree getTree() {
        return navigationTree;
    }

    /**
     * This method is used to remove menu node selected listener.
     * 
     * @param o Listener to be removed.
     * @return true if listener is removed, else false.
     */
    public boolean removeMenuNodeSelectedListener(MenuNodeSelectedListener o) {
        return menuNodeSelectedListeners.remove(o);
    }

    /**
     * This method is used to add menu node selected listener.
     * 
     * @param e Listener to be added.
     * @return true if listener is added, else false.
     */
    public boolean addMenuNodeSelectedListener(MenuNodeSelectedListener e) {
        return menuNodeSelectedListeners.add(e);
    }

    // </editor-fold>
    // PROTECTED
    /**
     * Variable to store EDS Application controller object.
     */
    protected EdsApplicationController controller;
    /**
     * Variable to store navigation tree.
     */
    protected Tree navigationTree;
    // PRIVATE
    /**
     * Variable to store the panel title.
     */
    private String caption;
    /**
     * Variable to store change listener.
     */
    private Property.ValueChangeListener changeListener;
    /**
     * Variable to store current node of navigation tree.
     */
    private A_EdsNavigationNode currentNode;
    /**
     * Variable to store current root node of navigation tree.
     */
    private A_EdsNavigationNode currentRootNode;
    /**
     * Variable to store list of Menu node selected listeners.
     */
    private ArrayList<MenuNodeSelectedListener> menuNodeSelectedListeners;

    /**
     * Initialization method. This method initializes the navigation tree and adds values to it.
     */
    private void init() {
        this.navigationTree = new Tree();
        this.currentNode = null;
        this.currentRootNode = null;
        this.menuNodeSelectedListeners = new ArrayList<MenuNodeSelectedListener>();

        if (caption == null) {
            setSizeUndefined();
            addComponent(navigationTree);
        } else {
            Panel navigationTreePanel = new Panel(this.caption);
            navigationTreePanel.setSizeFull();

            HorizontalLayout navigationTreeContainer = new HorizontalLayout();
            navigationTreeContainer.setSizeFull();

            navigationTreeContainer.addComponent(this.navigationTree);

            navigationTreePanel.setContent(navigationTreeContainer);
            addComponent(navigationTreePanel);
        }

        this.navigationTree.setItemStyleGenerator(new Tree.ItemStyleGenerator() {
            public String getStyle(Object itemId) {
                return ((A_EdsNavigationNode) itemId).getStyleName();
            }
        });

        initListeners();
        navigationTree.addListener(changeListener);
    }

    /**
     * Listener initialization method. This method is used to initialize listener to the navigation tree.
     */
    protected void initListeners() {
        // Implementation of events onEnter mechanism
        // Events onExit non select-able nodes are not triggered
        this.changeListener = new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                A_EdsNavigationNode node = (A_EdsNavigationNode) event.getProperty().getValue();

                if (node == null) {
                    return;
                }

                if (node.isSelectable()) {
                    if (node != currentNode) {
                        if (currentNode != null) {
                            currentNode.onExit();
                        }
                        fireMenuNodeSelected(node);

                        currentNode = node;
                        currentRootNode = (A_EdsNavigationNode) getRootNode(currentNode);
                        node.onEnter();
                    }
                } else {
                    if (navigationTree.isExpanded(node)) {
                        navigationTree.collapseItem(node);
                    } else {
                        navigationTree.expandItem(node);
                    }

                    if (currentNode != null) {
                        navigationTree.setValue(currentNode);
                    } else {
                        navigationTree.unselect(node);
                    }
                }
            }
        };
        navigationTree.addListener(changeListener);

        // Lets have a single root node unfolded at a time.
        navigationTree.addListener(new Tree.ExpandListener() {
            public void nodeExpand(ExpandEvent event) {
                A_EdsNavigationNode expandedNode = (A_EdsNavigationNode) event.getItemId();
                A_EdsNavigationNode newRootNode = (A_EdsNavigationNode) getRootNode(expandedNode);
                if (newRootNode != currentRootNode) {
                    navigationTree.collapseItem(currentRootNode);
                    currentRootNode = newRootNode;
                }
            }
        });

        this.navigationTree.setImmediate(true);
    }

    /**
     * This method is used to retrieve the root node of the navigation tree.
     * 
     * @param itemId Item id of the item/node's root to be retrieved.
     * @return Item ID of root node.
     */
    private Object getRootNode(Object itemId) {
        if (navigationTree.isRoot(itemId)) {
            return itemId;
        } else {
            return getRootNode(navigationTree.getParent(itemId));
        }
    }

    /**
     * This method is used to fire the menu node which is being selected.
     * 
     * @param selectedNode selected node.
     */
    private void fireMenuNodeSelected(A_EdsNavigationNode selectedNode) {
        MenuNodeSelectedEvent event = new MenuNodeSelectedEvent(selectedNode);

        for (MenuNodeSelectedListener listener : menuNodeSelectedListeners) {
            if (event.isConsumed()) {
                break;
            }

            listener.nodeSelected(event);
        }
    }
}
