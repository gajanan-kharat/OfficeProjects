package com.inetpsa.eds.application.menu.dashboard;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.dashboard.DashBoardEDSListView;
import com.inetpsa.eds.tools.filter.drift.EdsDriftFilter;
import com.vaadin.ui.Layout;

/**
 * This class represents component representing the node "Drifted EDS" in menu "Dashboard."
 * 
 * @author Geometric Ltd.
 */
public class EN_DB_DriftEdsNode extends A_EN_DashBoardNode {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EN_DB_DriftEdsNode(EdsApplicationController controller) {
        super(controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.dashboard.A_EN_DashBoardNode#onEnter()
     */
    @Override
    public void onEnter() {
        super.onEnter();
        getController().hideActionBar();
        getController().setContent(getView());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onExit()
     */
    @Override
    public void onExit() {
        getController().getActionBar().clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getCaption()
     */
    @Override
    public String getCaption() {
        return controller.getBundle().getString("menu-app-der");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getURIfragment()
     */
    @Override
    public String getURIfragment() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#isSelectable()
     */
    @Override
    public boolean isSelectable() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#areChildrenAllowed()
     */
    @Override
    public boolean areChildrenAllowed() {
        return false;
    }

    /**
     * This method is used to retrieve the "Drifted EDS" view.
     * 
     * @return "Drifted EDS" component.
     */
    public Layout getView() {
        if (edsListView == null) {
            edsListView = new DashBoardEDSListView(getController());
            edsListView.getFilterPanel().getFilterManagerView().addFilter(new EdsDriftFilter(controller));
        }

        edsListView.getFilterPanel().getFilterManagerView().updateEdsList();

        return edsListView;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the "Drifted EDS" list view component.
     */
    private DashBoardEDSListView edsListView;

    /**
     * Initialization method.
     */
    private void init() {
        this.edsListView = null;
    }
}
