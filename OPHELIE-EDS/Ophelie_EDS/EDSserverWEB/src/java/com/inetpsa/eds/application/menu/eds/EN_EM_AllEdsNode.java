package com.inetpsa.eds.application.menu.eds;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.dashboard.OverallEDSListView;
import com.inetpsa.eds.tools.filter.normal.EdsNormalFilter;
import com.inetpsa.eds.tools.filter.search.EdsSearchFilter;
import java.util.HashMap;

/**
 * This class represents the component representing the node "All EDS" of "EDS Browser" menu.
 * 
 * @author Geometric Ltd.
 */
public class EN_EM_AllEdsNode extends A_EN_EdsMenuNode {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EN_EM_AllEdsNode(EdsApplicationController controller) {
        super(controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.eds.A_EN_EdsMenuNode#onEnter()
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
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onEnter(java.util.HashMap)
     */
    @Override
    public void onEnter(HashMap<String, String> parameters) {
        getController().hideActionBar();
        getController().setContent(getView(parameters));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onExit()
     */
    @Override
    public void onExit() {
        getController().getActionBar().clear();
        getController().getContent().removeAllComponents();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getCaption()
     */
    @Override
    public String getCaption() {
        return controller.getBundle().getString("menu-app-all-fich");
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
     * This method is used to retrieve the EDS list view.
     * 
     * @return Component for EDS list view.
     */
    public OverallEDSListView getView() {
        return getView(null);
    }

    /**
     * This method is used to retrieve the "All EDS" view.
     * 
     * @param parameters Map of parameters to the filter.
     * @return Component for EDS list view.
     */
    public OverallEDSListView getView(HashMap<String, String> parameters) {
        if (edsListView == null) {
            edsListView = new OverallEDSListView(getController());
            edsListView.getFilterPanel().getFilterManagerView().addFilter(new EdsNormalFilter(controller));
        }

        if (parameters != null) {
            if (parameters.containsKey("search")) {
                edsListView.getFilterPanel().getFilterManagerView().addFilter(new EdsSearchFilter(parameters.get("search"), controller));
            }
        } else {
            edsListView.getFilterPanel().getFilterManagerView().updateEdsList();
        }

        edsListView.getFilterPanel().getFilterManagerView().updateEdsList();

        return edsListView;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the "All EDS" list view component.
     */
    private OverallEDSListView edsListView;

    /**
     * Initialization method.
     */
    private void init() {
        this.edsListView = null;
    }
}
