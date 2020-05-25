package com.inetpsa.eds.tools.filter.affectation;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter according to assignment
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsAffected extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "generic-data-affectation";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of Eds application
     */
    public FF_EdsAffected(EdsApplicationController controller) {
        super(FACTORY_NAME, controller);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Eds affected filter factory
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
        return new FE_EdsAffected(controller);
    }
}
