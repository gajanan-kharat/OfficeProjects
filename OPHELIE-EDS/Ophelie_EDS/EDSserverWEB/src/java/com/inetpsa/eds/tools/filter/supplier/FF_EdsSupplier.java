package com.inetpsa.eds.tools.filter.supplier;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet as a supplier.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsSupplier extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant variable to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "generic-data-represent-FNR";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsSupplier(EdsApplicationController controller) {
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
        return new FE_EdsSupplier(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize EDS supplier filter factory
     */
    private void init() {
    }
}
