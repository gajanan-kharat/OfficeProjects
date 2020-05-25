package com.inetpsa.eds.application.menu.edsnode;

import com.inetpsa.eds.application.menu.EN_ContainerNode;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.uri.FragmentManager;
import com.inetpsa.eds.dao.model.EdsEds;

/**
 * This class is used to add a EDS node to the Navigation menu.
 * 
 * @author Geometric Ltd.
 */
public class EN_EdsNode extends EN_ContainerNode {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param eds EDS details of EDS to be added to navigation menu.
     */
    public EN_EdsNode(EdsApplicationController controller, EdsEds eds) {
        super(controller, eds.toImplicitName());
        this.eds = eds;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.EN_ContainerNode#getURIfragment()
     */
    @Override
    public String getURIfragment() {
        return FragmentManager.formatURLFragmentForEDS(eds);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getStyleName()
     */
    @Override
    public String getStyleName() {
        return "eds-node";
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the EDS details.
     */
    private EdsEds eds;

    /**
     * Initialization method.
     */
    private void init() {
    }
}
