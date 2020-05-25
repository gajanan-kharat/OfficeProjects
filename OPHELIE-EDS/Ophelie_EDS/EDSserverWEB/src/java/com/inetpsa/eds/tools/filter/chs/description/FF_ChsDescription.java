package com.inetpsa.eds.tools.filter.chs.description;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet by CHS Description.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class FF_ChsDescription extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "chs-filter-desc";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_ChsDescription(EdsApplicationController controller) {
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
        return new FE_ChsDescription(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for name
     */
    private void init() {
    }
}
