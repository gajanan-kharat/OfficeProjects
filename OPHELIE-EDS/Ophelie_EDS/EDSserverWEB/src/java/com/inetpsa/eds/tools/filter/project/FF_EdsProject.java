/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.tools.filter.project;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet for a project
 * 
 * @author Geometric Ltd.
 */
public class FF_EdsProject extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant variable that hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "filter-project";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_EdsProject(EdsApplicationController controller) {
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
        return new FE_EdsProject(controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Filter factory for a project
     */
    private void init() {
    }
}
