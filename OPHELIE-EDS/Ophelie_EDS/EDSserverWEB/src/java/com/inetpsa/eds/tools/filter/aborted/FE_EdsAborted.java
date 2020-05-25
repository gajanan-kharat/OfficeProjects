package com.inetpsa.eds.tools.filter.aborted;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.Label;

/**
 * Filter Editor plug according to the Eds discontinued.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsAborted extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsAborted(EdsApplicationController controller) {
        super(controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_FilterEditor#createFilter()
     */
    @Override
    public A_EdsFilter createFilter() throws E_FilterNotReady {
        return new EdsAbortedFilter(controller);
    }

    // PROTECTED
    // PRIVATE

    /**
     * Initialize Eds aborted filter editor
     */
    private void init() {
        this.setSpacing(true);

        this.addComponent(new Label(controller.getBundle().getString("filter-limit-message")));
    }
}
