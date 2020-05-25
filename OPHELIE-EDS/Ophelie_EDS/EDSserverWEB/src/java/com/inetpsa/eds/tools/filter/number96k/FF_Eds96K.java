package com.inetpsa.eds.tools.filter.number96k;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet by number 96XXX.
 * 
 * @author Geometric Ltd.
 */
public class FF_Eds96K extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "filter-96";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_Eds96K(EdsApplicationController controller) {
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
        return new FE_Eds96K(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for a number 96XXX
     */
    private void init() {
    }
}
