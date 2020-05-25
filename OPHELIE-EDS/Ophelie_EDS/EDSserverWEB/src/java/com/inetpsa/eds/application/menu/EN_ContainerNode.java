package com.inetpsa.eds.application.menu;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode;

/**
 * Navigation node serving container. No view is associated.
 * 
 * @author Geometric Ltd.
 */
public class EN_ContainerNode extends A_EdsNavigationNode {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param caption panel title.
     */
    public EN_ContainerNode(EdsApplicationController controller, String caption) {
        super(controller);
        this.caption = caption;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getCaption()
     */
    @Override
    public String getCaption() {
        return caption;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#areChildrenAllowed()
     */
    @Override
    public boolean areChildrenAllowed() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#isSelectable()
     */
    @Override
    public boolean isSelectable() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getURIfragment()
     */
    @Override
    public String getURIfragment() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onEnter()
     */
    @Override
    public void onEnter() {
        // DO NOTHING
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onExit()
     */
    @Override
    public void onExit() {
        // DO NOTHING
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store panel title.
     */
    private String caption;

    /**
     * Initialization method.
     */
    private void init() {
    }
}
