package com.inetpsa.eds.tools.filter.subtype2;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter plug according subtype 2.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsSubtype2 extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant variable that hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "generic-data-organe-type-2";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsSubtype2(EdsApplicationController controller) {
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
        return new FE_EdsSubtype2(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Eds subtype 2 filter factory
     */
    private void init() {
    }
}
