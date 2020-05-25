package com.inetpsa.eds.application.menu.dashboard;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.dashboard.UnmodifiedEdsListView;
import com.vaadin.ui.Layout;

/**
 * This class represents component representing the node "No modified EDS" in menu "Dashboard".
 * 
 * @author Geometric Ltd.
 */
public class EN_DB_UnmodifiedEdsNode extends A_EN_DashBoardNode {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EN_DB_UnmodifiedEdsNode(EdsApplicationController controller) {
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
        return controller.getBundle().getString("menu-app-no-modif");
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
     * This method is used to retrieve the "No modified EDS" view.
     * 
     * @return "No modified EDS" component.
     */
    public Layout getView() {
        if (edsListView == null) {
            edsListView = new UnmodifiedEdsListView(controller);
        } else {
            edsListView.getFilterPanel().getFilterManagerView().updateEdsList();
        }

        return edsListView;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the "No modified EDS" list view component.
     */
    private UnmodifiedEdsListView edsListView;

    /**
     * Initialization method.
     */
    private void init() {
        this.edsListView = null;
    }
}
