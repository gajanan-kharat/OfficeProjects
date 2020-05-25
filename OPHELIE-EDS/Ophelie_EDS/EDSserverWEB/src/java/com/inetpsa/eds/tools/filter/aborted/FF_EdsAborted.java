package com.inetpsa.eds.tools.filter.aborted;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter plug according to the discontinued Eds.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsAborted extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant variable that hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "eds-version-fiche-abandonnee";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsAborted(EdsApplicationController controller) {
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
        return new FE_EdsAborted(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Eds aborted filter factory
     */
    private void init() {
    }
}
