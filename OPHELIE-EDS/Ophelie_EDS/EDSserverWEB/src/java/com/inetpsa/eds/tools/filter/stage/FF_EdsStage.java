package com.inetpsa.eds.tools.filter.stage;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet depending on the stage of validation
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsStage extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant variable that hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "mail-content-eds-stade";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of Eds application
     */
    public FF_EdsStage(EdsApplicationController controller) {
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
        return new FE_EdsStage(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize EdsStageFilter
     */
    private void init() {
    }
}
