package com.inetpsa.eds.tools.filter.bttbt;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet depending on the type of network.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsBtTbt extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "filter-bt-tbt";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsBtTbt(EdsApplicationController controller) {
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
        return new FE_EdsBtTbt(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for type of network
     */
    private void init() {
    }
}
