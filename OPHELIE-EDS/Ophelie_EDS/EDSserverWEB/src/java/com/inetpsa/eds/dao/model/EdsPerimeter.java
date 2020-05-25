package com.inetpsa.eds.dao.model;

// Generated 14 juin 2012 16:43:00 by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * EdsPerimeter generated by hbm2java
 */
@XmlType
public class EdsPerimeter implements java.io.Serializable {
    /**
     * Constant to hold value of HIDDEN
     */
    public static final int HIDDEN = -1;
    /**
     * Constant to hold value of INACTIVE
     */
    public static final int INACTIVE = 0;
    /**
     * Constant to hold value of ACTIVE
     */
    public static final int ACTIVE = 1;
    /**
     * String Variable to hold the value for Column PE_ID of Table OPLQTPER
     */
    private String peId;
    /**
     * String Variable to hold the value for Column PE_NAME of Table OPLQTPER
     */
    private String peName;
    /**
     * String Variable to hold the value for Column PE_ACTIVE of Table OPLQTPER
     */
    private int peActive;
    /**
     * Set Collection to hold the values for Table OPLQTUSE
     */
    private Set edsUsers = new HashSet(0);

    /**
     * Default Constructor
     */
    public EdsPerimeter() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param peId partner id
     * @param peName partner name
     * @param peActive Partner active value
     */
    public EdsPerimeter(String peId, String peName, int peActive) {
        this.peId = peId;
        this.peName = peName;
        this.peActive = peActive;
    }

    /**
     * Parameterized Constructor
     * 
     * @param peId partner id
     * @param peName partner name
     * @param peActive Partner active value
     * @param edsUsers Set of EDS users
     */
    public EdsPerimeter(String peId, String peName, int peActive, Set edsUsers) {
        this.peId = peId;
        this.peName = peName;
        this.peActive = peActive;
        this.edsUsers = edsUsers;
    }

    /**
     * Function to get peId
     * 
     * @return the Value of peId
     */
    @XmlTransient
    public String getPeId() {
        return this.peId;
    }

    /**
     * Function to set peId
     * 
     * @param peId Id
     */
    public void setPeId(String peId) {
        this.peId = peId;
    }

    /**
     * Function to get peName
     * 
     * @return the Value of peName
     */
    @XmlElement
    public String getPeName() {
        return this.peName;
    }

    /**
     * Function to set peName
     * 
     * @param peName Name
     */
    public void setPeName(String peName) {
        this.peName = peName;
    }

    /**
     * Function to get peActive
     * 
     * @return the Value of peActive
     */
    @XmlElement(name = "is-active")
    public int getPeActive() {
        return peActive;
    }

    /**
     * Function to set peActive
     * 
     * @param peActive Active
     */
    public void setPeActive(int peActive) {
        this.peActive = peActive;
    }

    /**
     * Function to get edsUsers
     * 
     * @return the Value of edsUsers
     */
    @XmlTransient
    public Set getEdsUsers() {
        return this.edsUsers;
    }

    /**
     * Function to set edsUsers
     * 
     * @param edsUsers set of EdsUser
     */
    public void setEdsUsers(Set edsUsers) {
        this.edsUsers = edsUsers;
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
        final EdsPerimeter other = (EdsPerimeter) obj;
        if ((this.peId == null) ? (other.peId != null) : !this.peId.equals(other.peId)) {
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
        hash = 23 * hash + (this.peId != null ? this.peId.hashCode() : 0);
        return hash;
    }
}
