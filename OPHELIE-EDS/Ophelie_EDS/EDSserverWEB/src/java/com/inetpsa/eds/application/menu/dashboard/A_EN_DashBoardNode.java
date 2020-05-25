package com.inetpsa.eds.application.menu.dashboard;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode;

/**
 * This is an abstract class which represents Dashboard node.
 * 
 * @author Geometric Ltd.
 */
public abstract class A_EN_DashBoardNode extends A_EdsNavigationNode {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public A_EN_DashBoardNode(EdsApplicationController controller) {
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
