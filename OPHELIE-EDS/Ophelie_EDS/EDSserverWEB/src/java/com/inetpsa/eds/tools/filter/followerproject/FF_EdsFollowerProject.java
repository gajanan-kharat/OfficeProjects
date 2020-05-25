package com.inetpsa.eds.tools.filter.followerproject;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet as a follower project.
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsFollowerProject extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "filter-folower-p-title";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsFollowerProject(EdsApplicationController controller) {

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
        return new FE_EdsFollowerProject(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter factory for a follower project
     */
    private void init() {
    }
}
