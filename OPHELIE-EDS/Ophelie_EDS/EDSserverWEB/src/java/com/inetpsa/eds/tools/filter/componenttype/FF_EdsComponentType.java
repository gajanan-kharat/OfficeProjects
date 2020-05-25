package com.inetpsa.eds.tools.filter.componenttype;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet by type of component.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsComponentType extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "menu-project-tab-model";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsComponentType(EdsApplicationController controller) {
        super(FACTORY_NAME, controller);
        init();
    }

    @Override
    protected A_FilterEditor buildEditor() {
        return new FE_EdsComponentType(controller);
    }

    // PROTECTED
    // PRIVATE

    /**
     * Initialize filter factory for type of Component
     */
    private void init() {
    }
}
