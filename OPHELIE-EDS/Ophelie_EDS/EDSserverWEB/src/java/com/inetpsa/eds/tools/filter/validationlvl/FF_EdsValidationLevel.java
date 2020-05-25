package com.inetpsa.eds.tools.filter.validationlvl;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Filter factory sheet at a level of EDS validation.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsValidationLevel extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant variable to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "generic-data-organe-level";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsValidationLevel(EdsApplicationController controller) {
        super(FACTORY_NAME, controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_FilterFactory#buildEditor()
     */
    @Override
    protected A_FilterEditor buildEditor() {
        return new FE_EdsValidationLevel(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize EDS validation level filter factory
     */
    private void init() {
    }
}
