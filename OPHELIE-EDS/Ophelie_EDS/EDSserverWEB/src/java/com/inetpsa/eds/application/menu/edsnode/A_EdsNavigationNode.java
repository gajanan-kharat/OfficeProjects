package com.inetpsa.eds.application.menu.edsnode;

import com.inetpsa.eds.application.EdsApplicationController;
import java.io.Serializable;
import java.util.HashMap;

/**
 * This is an abstract class which represents EDS navigation node.
 * 
 * @author Geometric Ltd.
 */
public abstract class A_EdsNavigationNode implements Serializable {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public A_EdsNavigationNode(EdsApplicationController controller) {
        this.controller = controller;
    }

    /**
     * This method is used to retrieve EDS application controller object.
     * 
     * @return EDS application controller object.
     */
    public EdsApplicationController getController() {
        return controller;
    }

    /**
     * This method gets called whenever the node is visited.
     */
    public abstract void onEnter();

    /**
     * This method gets called at the entry node. Used to pass parameters. To override if necessary.
     * 
     * @param parameters map of parameters.
     */
    public void onEnter(HashMap<String, String> parameters) {
        // Do nothing
    }

    /**
     * This method gets called at the exit of the node.
     */
    public abstract void onExit();

    /**
     * This method gets the window title.
     * 
     * @return The description of the node to be displayed in the menu.
     */
    public abstract String getCaption();

    /**
     * This method retrieves the URI fragment of the node.
     * 
     * @return URI fragment associated with the node.
     */
    public abstract String getURIfragment();

    /**
     * This method is used to decide if the view is associated with the node.
     * 
     * @return true if a view is associated with the node, false otherwise.
     */
    public abstract boolean isSelectable();

    /**
     * This method is used to decide if children are allowed for current node.
     * 
     * @return true if the node can contain other nodes, false otherwise.
     */
    public abstract boolean areChildrenAllowed();

    /**
     * Overloading methods to give a particular CSS style to a navigation node.
     * 
     * @return CSS style to apply to this node, null if no specific style.
     */
    public String getStyleName() {
        return null;
    }

    // PROTECTED
    /**
     * Variable to store the EDS application controller object.
     */
    protected EdsApplicationController controller;
}
