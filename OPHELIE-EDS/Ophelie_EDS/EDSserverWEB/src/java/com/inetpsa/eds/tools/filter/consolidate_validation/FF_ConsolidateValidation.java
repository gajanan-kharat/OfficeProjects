package com.inetpsa.eds.tools.filter.consolidate_validation;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet for a validation of consolidate stage
 * 
 * @author Geometric Ltd.
 */
public class FF_ConsolidateValidation extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "menu-project-tab-model";

    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public FF_ConsolidateValidation(EdsApplicationController controller) {
        super(FACTORY_NAME, controller);
        init();
    }

    @Override
    protected A_FilterEditor buildEditor() {
        return new FE_ConsolidateValidation(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize factory filter for a validation of consolidate stage
     */
    private void init() {
    }

}
