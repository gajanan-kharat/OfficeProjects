package com.inetpsa.eds.tools.filter;

import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.ui.HorizontalLayout;

/**
 * This abstract class is a component for displaying a GUI for creating and customizing a filter. one Subclass must be defined for each filter.
 * 
 * @author Geometric Ltd.
 * @see A_EdsFilter, A_FilterFactory
 */
public abstract class A_FilterEditor extends HorizontalLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public A_FilterEditor(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method allows you to create a GUI to build a filter.
     * 
     * @return The resulting filter
     * @throws E_FilterNotReady Exception is thrown while creating filter if the prerequisites are not met.
     */
    public abstract A_EdsFilter createFilter() throws E_FilterNotReady;

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EDS application controller.
     */
    protected EdsApplicationController controller;

    /**
     * Initialize filter editor
     */
    private void init() {
    }
}
