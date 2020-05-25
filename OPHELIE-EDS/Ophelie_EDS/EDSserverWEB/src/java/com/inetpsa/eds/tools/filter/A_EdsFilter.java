package com.inetpsa.eds.tools.filter;

import java.io.Serializable;

import org.hibernate.criterion.DetachedCriteria;

import com.inetpsa.eds.application.EdsApplicationController;

/**
 * Abstract class that serves as a model for filters for querying EDS sheets. The filters use a feature of the Hibernate API to dynamically construct
 * queries related to sheets.
 * 
 * @author Geometric Ltd.
 * @see A_FilterEditor, A_FilterFactory
 */
public abstract class A_EdsFilter implements Serializable {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public A_EdsFilter(EdsApplicationController controller) {
        this.isRemoveable = true;
        this.isVisible = true;
        this.controller = controller;
    }

    /**
     * This method check if filter is removable.
     * 
     * @return check if removable
     */
    public boolean isRemoveable() {
        return isRemoveable;
    }

    /**
     * This method set removable value.
     * 
     * @param isRemoveable check if removable
     */
    public void setRemoveable(boolean isRemoveable) {
        this.isRemoveable = isRemoveable;
    }

    /**
     * This method check if filter is visible.
     * 
     * @return check if removable
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * This method set visibility value.
     * 
     * @param isVisible check if visible
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * This method used to build hibernate queries.
     * 
     * @param criteriaQuery The application in which to add the current filter.
     * @return The modified query.
     */
    public abstract DetachedCriteria buildQuery(DetachedCriteria criteriaQuery);

    /**
     * This method used to display the current filter in the filterManager.
     * 
     * @return The description of current filter.
     */
    public abstract String describe();

    // PROTECTED
    /**
     * Variable to check if filter is removable.
     */
    protected boolean isRemoveable;
    /**
     * Variable to check if filter is visible.
     */
    protected boolean isVisible;
    /**
     * Variable to hold the value of EDS application controller.
     */
    protected EdsApplicationController controller;
}
