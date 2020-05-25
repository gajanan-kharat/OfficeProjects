package com.inetpsa.eds.tools.filter.description;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet by description.
 */
public class FF_EdsDescription extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "filter-description";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsDescription(EdsApplicationController controller) {
        super(FACTORY_NAME, controller);
        init();
    }

    @Override
    protected A_FilterEditor buildEditor() {
        return new FE_EdsDescription(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for description
     */
    private void init() {
    }
}
