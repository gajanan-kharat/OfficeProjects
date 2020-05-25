package com.inetpsa.eds.tools.filter.subtype1;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter plug according to subtype 1.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsSubtype1 extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant variable that hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "generic-data-organe-type-1";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of Eds application
     */
    public FF_EdsSubtype1(EdsApplicationController controller) {
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
        return new FE_EdsSubtype1(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Eds subtype 1 filter factory.
     */
    private void init() {
    }
}
