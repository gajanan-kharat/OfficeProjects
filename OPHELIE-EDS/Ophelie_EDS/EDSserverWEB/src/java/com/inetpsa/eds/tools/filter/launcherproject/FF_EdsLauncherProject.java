package com.inetpsa.eds.tools.filter.launcherproject;

import com.inetpsa.eds.EdsApplication;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter plug in the launcher project.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsLauncherProject extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "filter-project-launcher";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsLauncherProject(EdsApplicationController controller) {
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
        return new FE_EdsLauncherProject(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for project launcher
     */
    private void init() {
    }
}
