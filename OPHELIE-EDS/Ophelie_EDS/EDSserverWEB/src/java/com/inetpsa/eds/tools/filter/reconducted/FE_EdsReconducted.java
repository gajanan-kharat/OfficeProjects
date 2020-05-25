package com.inetpsa.eds.tools.filter.reconducted;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.Label;

/**
 * Filter Editor page as renewed forms.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsReconducted extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsReconducted(EdsApplicationController controller) {
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
        return new EdsReconductedFilter(controller);
    }

    // PROTECTED
    // PRIVATE

    /**
     * Initialize filter editor for Eds renewed
     */
    private void init() {
        this.setSpacing(true);

        this.addComponent(new Label(controller.getBundle().getString("filter-mess-rec")));
    }
}
