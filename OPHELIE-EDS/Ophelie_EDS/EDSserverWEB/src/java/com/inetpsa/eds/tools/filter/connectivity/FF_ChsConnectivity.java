package com.inetpsa.eds.tools.filter.connectivity;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter Connected Chs
 * 
 * @author Joao Costa @ Alter Frame
 */
public class FF_ChsConnectivity extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "filter-connectivity";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_ChsConnectivity(EdsApplicationController controller) {

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
        return new FE_ChsConnectivity(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for a follower project
     */
    private void init() {
    }
}
