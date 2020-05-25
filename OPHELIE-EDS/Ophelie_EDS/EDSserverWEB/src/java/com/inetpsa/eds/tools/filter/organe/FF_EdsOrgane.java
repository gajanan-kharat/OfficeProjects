package com.inetpsa.eds.tools.filter.organe;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet according to body
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsOrgane extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "filter-organe-title";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsOrgane(EdsApplicationController controller) {
        super(FACTORY_NAME, controller);
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Factory filter sheet according to body
     */
    private void init() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_FilterFactory#buildEditor()
     */
    @Override
    protected A_FilterEditor buildEditor() {
        return new FE_EdsOrgane(controller);
    }
}
