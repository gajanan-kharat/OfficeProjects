package com.inetpsa.eds.tools.filter.manager;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter plug in the PSA responsible.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsManager extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "generic-data-represent-responsible";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsManager(EdsApplicationController controller) {
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
        return new FE_EdsManager(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for PSA responsible
     */
    private void init() {
    }
}
