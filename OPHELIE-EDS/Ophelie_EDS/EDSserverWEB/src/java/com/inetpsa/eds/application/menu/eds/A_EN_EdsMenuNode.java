package com.inetpsa.eds.application.menu.eds;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode;

/**
 * This is an abstract class which represents "EDS Browser" node.
 * 
 * @author Geometric Ltd.
 */
public abstract class A_EN_EdsMenuNode extends A_EdsNavigationNode {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public A_EN_EdsMenuNode(EdsApplicationController controller) {
        super(controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onEnter()
     */
    @Override
    public void onEnter() {
        controller.getUriFragmentUtility().setFragment(null, false);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialization method.
     */
    private void init() {
    }
}
