package com.inetpsa.eds.tools.filter.administrator;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter plug according to the administrator
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsAdministrator extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "generic-data-represent";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of Eds application
     */
    public FF_EdsAdministrator(EdsApplicationController controller) {
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
        return new FE_EdsAdministrator(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Eds administrator filter factory
     */
    private void init() {
    }
}
