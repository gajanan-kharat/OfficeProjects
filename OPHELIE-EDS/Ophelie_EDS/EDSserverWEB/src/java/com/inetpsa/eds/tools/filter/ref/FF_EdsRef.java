package com.inetpsa.eds.tools.filter.ref;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet by reference.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsRef extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant variable that hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "hist-ref";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsRef(EdsApplicationController controller) {
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
        return new FE_EdsRef(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for Eds reference
     */
    private void init() {
    }
}
