package com.inetpsa.eds.application.menu.eds;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.dashboard.OverallEDSListView;
import com.inetpsa.eds.tools.filter.affectation.EdsAffectationFilter;
import com.inetpsa.eds.tools.filter.owner_affectation.EdsOwnerAffectationFilter;
import com.inetpsa.eds.tools.filter.subscribed.EdsSubscribedFilter;
import java.util.HashMap;

/**
 * This class represents the component representing the node "My assignments" in menu "EDS browser."
 * 
 * @author Geometric Ltd.
 */
public class EN_EM_AffectedEdsNode extends A_EN_EdsMenuNode {
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EN_EM_AffectedEdsNode(EdsApplicationController controller) {
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
        return controller.getBundle().getString("menu-app-affect");
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
     * This method is used to retrieve the "My assignments" view.
     * 
     * @param parameters Map of parameters to the filter.
     * @return Component for EDS list view.
     */
    public OverallEDSListView getView(HashMap<String, String> parameters) {
        if (edsListView == null) {
            edsListView = new OverallEDSListView(getController());
            edsListView.getFilterPanel().getFilterManagerView()
                    .addFilter(new EdsOwnerAffectationFilter(controller.getAuthenticatedUser(), controller));
        }

        edsListView.getFilterPanel().getFilterManagerView().updateEdsList();

        return edsListView;
    }

    // PRIVATE
    /**
     * Variable to store the "My assignments" list view component.
     */
    private OverallEDSListView edsListView;

    /**
     * Initialization method.
     */
    private void init() {
        this.edsListView = null;
    }
}
