package com.inetpsa.eds.application.content.eds.currentconsumption.robust;

import java.io.Serializable;

/**
 * This class provide select options
 * 
 * @author Geometric Ltd.
 */
public class ChoixMasse implements Serializable {
    // PUBLIC
    /**
     * Default Constructor
     */
    public ChoixMasse() {
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param name Name of Option
     * @param id Id of Option
     */
    public ChoixMasse(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // PRIVATE
    /**
     * Variable to hold name
     */
    private String name;
    /**
     * Variable to hold Id
     */
    private int id;

    /**
     * Initialize Select options
     */
    private void init() {
    }

    /**
     * This method returns id
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * This method set id
     * 
     * @param id Id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * This method set name
     * 
     * @param name Name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }

}
