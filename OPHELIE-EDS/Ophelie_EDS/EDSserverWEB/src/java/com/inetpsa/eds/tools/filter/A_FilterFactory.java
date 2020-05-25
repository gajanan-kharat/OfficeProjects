package com.inetpsa.eds.tools.filter;

import com.inetpsa.eds.application.EdsApplicationController;
import java.io.Serializable;

/**
 * This abstract class is a factory to inherit for each filter. It allows publishers to create filter and build the filters.
 * 
 * @author Geometric Ltd.
 * @see A_EdsFilter, A_FilterEditor
 */
public abstract class A_FilterFactory implements Comparable<A_FilterFactory>, Serializable {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param name Name of Filter
     * @param controller Controller of EDS application
     */
    public A_FilterFactory(String name, EdsApplicationController controller) {
        this.name = name;
        this.controller = controller;
        init();
    }

    /**
     * This method create filter Editor.
     * 
     * @return The filter Editor
     */
    public A_FilterEditor createEditor() {
        editor = buildEditor();
        return editor;
    }

    /**
     * This method create filter.
     * 
     * @return The filter
     * @throws E_FilterNotReady Exception thrown while creating new filter if the prerequisites are not met.
     */
    public A_EdsFilter createFilter() throws E_FilterNotReady {
        if (editor == null) {
            throw new E_FilterNotReady("Null Editor : " + name);
        }
        return editor.createFilter();
    }

    /**
     * This method compare current filter Factory to the specified filter factory
     * 
     * @param o Filter factory to be compared.
     * @return Difference between name
     */
    public int compareTo(A_FilterFactory o) {
        return name.compareTo(o.name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final A_FilterFactory other = (A_FilterFactory) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return controller.getBundle().getString(name);
    }

    // PROTECTED
    /**
     * variable to hold Name of filter
     */
    protected String name;
    /**
     * Variable to hold filter editor value
     */
    protected A_FilterEditor editor;
    /**
     * Variable to hold value of EDS application filter.
     */
    protected EdsApplicationController controller;

    /**
     * This method builds Filter editor
     * 
     * @return The filter Editor
     */
    protected abstract A_FilterEditor buildEditor();

    // PRIVATE
    /**
     * Initialize filter factory
     */
    private void init() {
    }
}
