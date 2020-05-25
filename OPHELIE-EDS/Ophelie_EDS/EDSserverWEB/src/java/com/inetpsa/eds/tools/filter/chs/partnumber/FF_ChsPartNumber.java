package com.inetpsa.eds.tools.filter.chs.partnumber;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet by Part Number.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class FF_ChsPartNumber extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "chs-filter-partnum";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_ChsPartNumber(EdsApplicationController controller) {
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
        return new FE_ChsPartNumber(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for name
     */
    private void init() {
    }
}
