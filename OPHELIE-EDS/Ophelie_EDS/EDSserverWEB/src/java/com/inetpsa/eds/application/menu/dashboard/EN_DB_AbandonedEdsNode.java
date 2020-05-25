package com.inetpsa.eds.application.menu.dashboard;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.dashboard.DashBoardEDSListView;
import com.inetpsa.eds.tools.filter.aborted.EdsAbortedFilter;
import com.vaadin.ui.Layout;

/**
 * This class represents the component representing the node "Sheets Discontinued Operations/Discarded sheets" in menu "Dashboard."
 * 
 * @author Geometric Ltd..
 */
public class EN_DB_AbandonedEdsNode extends A_EN_DashBoardNode {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EN_DB_AbandonedEdsNode(EdsApplicationController controller) {
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
        return controller.getBundle().getString("eds-fiches-abandonnees");
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
     * This method is used to retrieve the "Discarded sheets" view.
     * 
     * @return "Discarded sheets" component.
     */
    public Layout getView() {
        if (edsListView == null) {
            edsListView = new DashBoardEDSListView(getController());
            EdsAbortedFilter filter = new EdsAbortedFilter(getController());
            filter.setRemoveable(false);
            edsListView.getFilterPanel().getFilterManagerView().addFilter(filter);
        }

        edsListView.getFilterPanel().getFilterManagerView().updateEdsList();

        return edsListView;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the "Discarded sheets" list view component.
     */
    private DashBoardEDSListView edsListView;

    /**
     * Initialization method.
     */
    private void init() {
        this.edsListView = null;
    }
}
