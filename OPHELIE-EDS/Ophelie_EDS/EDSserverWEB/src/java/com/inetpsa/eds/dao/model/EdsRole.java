package com.inetpsa.eds.dao.model;

// Generated 14 juin 2012 16:43:00 by Hibernate Tools 3.2.1.GA

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * EdsRole generated by hbm2java
 */
@XmlType
public class EdsRole implements java.io.Serializable {
    /**
     * Constant to hold value of TYPE_SUPPLIER
     */
    public static final int TYPE_SUPPLIER = 1;
    /**
     * Constant to hold value of TYPE_PERIMETER
     */
    public static final int TYPE_PERIMETER = 2;
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
     * String Variable to hold the value for Column RO_ID of Table OPLQTROL
     */
    private String roId;
    /**
     * String Variable to hold the value for Column RO_NAME of Table OPLQTROL
     */
    private String roName;
    /**
     * String Variable to hold the value for Column RO_RIGHTS of Table OPLQTROL
     */
    private String roRights;
    /**
     * String Variable to hold the value for Column RO_FORM_RIGHTS of Table OPLQTROL
     */
    private String roFormRights;
    /**
     * int Variable to hold the value for Column RO_ACTIVE of Table OPLQTROL
     */
    private int roActive;
    /**
     * Integer Variable to hold the value for Column RO_TYPE of Table OPLQTROL
     */
    private Integer roType;
    /**
     * Set Collection to hold the values for Table OPLQTUSE
     */
    private Set edsUsers = new HashSet(0);
    /**
     * Set Collection to hold the values for String
     */
    private Set<String> appRights;
    /**
     * Set Collection to hold the values for String
     */
    private Set<String> formRights;

    /**
     * Default Constructor
     */
    public EdsRole() {
        init();
    }

    /**
     * Parameterized Constructor
     * 
     * @param roId Eds Role Id
     * @param roName Eds Role name
     * @param roActive Eds Role active
     */
    public EdsRole(String roId, String roName, int roActive) {
        init();
        this.roId = roId;
        this.roName = roName;
        this.roActive = roActive;
    }

    /**
     * Parameterized Constructor
     * 
     * @param roId Eds Role ID
     * @param roName Eds Role name
     * @param roActive Eds Role active
     * @param roRights Eds Role rights
     * @param roFormRights Eds Role form rights
     * @param roType Eds Role type
     * @param edsUsers set of EdsUser
     */
    public EdsRole(String roId, String roName, int roActive, String roRights, String roFormRights, Integer roType, Set edsUsers) {
        init();
        this.roId = roId;
        this.roName = roName;
        this.roActive = roActive;
        this.setRoRights(roRights);
        this.setRoFormRights(roFormRights);
        this.roType = roType;
        this.edsUsers = edsUsers;
    }

    /**
     * Function to get roId
     * 
     * @return the Value of roId
     */
    @XmlTransient
    public String getRoId() {
        return this.roId;
    }

    /**
     * Function to set roId
     * 
     * @param roId Eds Role Id
     */
    public void setRoId(String roId) {
        this.roId = roId;
    }

    /**
     * Function to get roName
     * 
     * @return the Value of roName
     */
    @XmlElement
    public String getRoName() {
        return this.roName;
    }

    /**
     * Function to set roName
     * 
     * @param roName Eds Role name
     */
    public void setRoName(String roName) {
        this.roName = roName;
    }

    /**
     * Function to get roActive
     * 
     * @return the Value of roActive
     */
    @XmlTransient
    public int getRoActive() {
        return roActive;
    }

    /**
     * Function to set roActive
     * 
     * @param roActive Eds Role active
     */
    public void setRoActive(int roActive) {
        this.roActive = roActive;
    }

    /**
     * Function to get roRights
     * 
     * @return the Value of roRights
     */
    @XmlTransient
    public String getRoRights() {
        return this.roRights;
    }

    /**
     * Function to set roRights and appRights
     * 
     * @param roRights Eds Role rights
     */
    public void setRoRights(String roRights) {
        this.roRights = roRights;

        this.appRights.clear();
        if (roRights != null) {
            this.appRights.addAll(Arrays.asList(roRights.split(";")));
        }
    }

    /**
     * Function to get all appRights
     * 
     * @return the Set of appRights
     */
    @XmlTransient
    public Set<String> getAllAppRights() {
        return this.appRights;
    }

    /**
     * Test if the EDS role contains the given app right
     * 
     * @param appRight The right to test
     * @return True if contained, false otherwise
     */
    public boolean appRightsContains(String appRight) {
        if (appRight == null)
            return false;

        return appRights.contains(appRight);
    }

    /**
     * Function to set roRights and appRights
     * 
     * @param rights Set of rights
     */
    public void setAllAppRigths(Collection<String> rights) {
        StringBuilder strBuilder = new StringBuilder();
        for (String right : rights) {
            strBuilder.append(right);
            strBuilder.append(';');
        }
        try {
            strBuilder.setLength(strBuilder.length() - 1);

            this.roRights = strBuilder.toString();
            this.appRights.clear();
            this.appRights.addAll(rights);
        } catch (StringIndexOutOfBoundsException ex) {
            this.roRights = "";
            this.appRights.clear();
        }
    }

    /**
     * Function to get roFormRights
     * 
     * @return the Value of roFormRights
     */
    @XmlTransient
    public String getRoFormRights() {
        return roFormRights;
    }

    /**
     * Function to set roFormRights and formRights
     * 
     * @param roFormRights Eds Role form rights
     */
    public void setRoFormRights(String roFormRights) {
        this.roFormRights = roFormRights;
        this.formRights.clear();
        if (roFormRights != null) {
            this.formRights.addAll(Arrays.asList(roFormRights.split(";")));
        }
    }

    /**
     * Function to get formRights
     * 
     * @return the Set of formRights
     */
    @XmlTransient
    public Set<String> getAllFormRights() {
        return this.formRights;
    }

    /**
     * Function to set formRights and roFormRights
     * 
     * @param rights set of form rights
     */
    public void setAllFormRigths(Collection<String> rights) {
        StringBuilder strBuilder = new StringBuilder();
        for (String right : rights) {
            strBuilder.append(right);
            strBuilder.append(';');
        }
        try {
            strBuilder.setLength(strBuilder.length() - 1);

            this.roFormRights = strBuilder.toString();
            this.formRights.clear();
            this.formRights.addAll(rights);
        } catch (StringIndexOutOfBoundsException ex) {
            this.roFormRights = "";
            this.formRights.clear();
        }
    }

    /**
     * Function to get roType
     * 
     * @return the Value of roType
     */
    @XmlTransient
    public Integer getRoType() {
        return roType;
    }

    /**
     * Function to set roType
     * 
     * @param roType Eds Role type
     */
    public void setRoType(Integer roType) {
        this.roType = roType;
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
     * @param edsUsers Eds User
     */
    public void setEdsUsers(Set edsUsers) {
        this.edsUsers = edsUsers;
    }

    /**
     * Function to get roType which equals TYPE_SUPPLIER
     * 
     * @return the Value of roType
     */
    public boolean isSupplier() {
        return roType != null && roType.equals(TYPE_SUPPLIER);
    }

    /**
     * Function to get roType which equals TYPE_PERIMETER
     * 
     * @return the Value of roType
     */
    public boolean isPerimeter() {
        return roType != null && roType.equals(TYPE_PERIMETER);
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
        final EdsRole other = (EdsRole) obj;
        if ((this.roId == null) ? (other.roId != null) : !this.roId.equals(other.roId)) {
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
        int hash = 3;
        hash = 29 * hash + (this.roId != null ? this.roId.hashCode() : 0);
        return hash;
    }

    /**
     * Initialize set of app rights and form rights
     */
    private void init() {
        this.appRights = new HashSet<String>();
        this.formRights = new HashSet<String>();
    }

    /**
     * Function to gets boolean value
     * 
     * @return the Value of the value of boolean
     */
    public boolean isActive() {
        if (roActive == 1) {
            return true;
        }
        return false;
    }
}