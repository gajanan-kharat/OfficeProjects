package com.inetpsa.eds.tools.filter.consolidate_validation;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.ComboBox;

/**
 * Filter editor plug for a validation of consolidate stage
 * 
 * @author Geometric Ltd.
 */
public class FE_ConsolidateValidation extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_ConsolidateValidation(EdsApplicationController controller) {
        super(controller);
        init();
    }

    @Override
    public A_EdsFilter createFilter() throws E_FilterNotReady {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of combo box
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize filter editor for a validation of Consolidate stage
     */
    private void init() {
    }
}
