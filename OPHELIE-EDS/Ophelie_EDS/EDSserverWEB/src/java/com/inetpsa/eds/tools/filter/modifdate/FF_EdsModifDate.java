package com.inetpsa.eds.tools.filter.modifdate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet according to modification date.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsModifDate extends A_FilterFactory {
    // PUBLIC
    /**
     * constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "eds-modif-date";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsModifDate(EdsApplicationController controller) {
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
        return new FE_EdsModifDate(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for modification date
     */
    private void init() {
    }
}
