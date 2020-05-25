package com.inetpsa.eds.tools.filter.officer;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter plug in the responsible development.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsOfficer extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "mail-content-eds-charge-dev";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsOfficer(EdsApplicationController controller) {
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
        return new FE_EdsOfficer(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize factory filter plug in the responsible development.
     */
    private void init() {
    }
}
