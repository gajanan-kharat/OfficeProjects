package com.inetpsa.eds.dao.model;

// Generated 14 juin 2012 16:43:00 by Hibernate Tools 3.2.1.GA

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * EdsComponentType generated by hbm2java
 */
@XmlType
public class EdsComponentType implements java.io.Serializable {
    /**
     * Constant to hold value of DELETED
     */
    public static final int DELETED = -1;
    /**
     * Constant to hold value of INACTIVE
     */
    public static final int INACTIVE = 0;
    /**
     * String Variable to hold the value for Column CT_ID of Table OPLQTCOT
     */
    private String ctId;
    /**
     * Variable to hold the value for Column CT_NAME of Table OPLQTCOT
     */
    private EdsWording ctName;
    /**
     * Variable to hold the value for Column CT_INDEX of Table OPLQTCOT
     */
    private int ctIndex;
    /**
     * String Variable to hold the value for Column CT_BTTBT_OK_FORMSET of Table OPLQTCOT
     */
    private String ctBttbtOkFormset;
    /**
     * String Variable to hold the value for Column CT_BTTBT_KO_FORMSET of Table OPLQTCOT
     */
    private String ctBttbtKoFormset;
    /**
     * Set Variable to hold the value for Column EDS_CT_ID of Table OPLQTCOT
     */
    private Set edsEdses = new HashSet(0);
    /**
     * Set Variable to hold the value for Column CT_BTTBT_OK_FORMSET of Table OPLQTCOT
     */
    private Set<String> bttbtFormsSet = null;
    /**
     * Set Variable to hold the value for Column CT_BTTBT_KO_FORMSET of Table OPLQTCOT
     */
    private Set<String> nonBttbtFormsSet = null;

    /**
     * Default Constructor
     */
    public EdsComponentType() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param ctId Component type id
     * @param ctName Component type name
     * @param ctIndex Component type index
     */
    public EdsComponentType(String ctId, EdsWording ctName, int ctIndex) {
        this.ctId = ctId;
        this.ctName = ctName;
        this.ctIndex = ctIndex;
    }

    /**
     * Parameterized Constructor
     * 
     * @param ctId Component type id
     * @param ctName Component type name
     * @param ctIndex Component type index
     * @param ctBttbtOkFormset Component type bttbt ok form set
     * @param ctBttbtKoFormset Component type bttbt ko form set
     * @param edsEdses
     */
    public EdsComponentType(String ctId, EdsWording ctName, int ctIndex, String ctBttbtOkFormset, String ctBttbtKoFormset, Set edsEdses) {
        this.ctId = ctId;
        this.ctName = ctName;
        this.ctIndex = ctIndex;
        this.setCtBttbtOkFormset(ctBttbtOkFormset);
        this.setCtBttbtKoFormset(ctBttbtKoFormset);
        this.edsEdses = edsEdses;
    }

    /**
     * Function to get ctId
     * 
     * @return Component type id
     */
    @XmlTransient
    public String getCtId() {
        return this.ctId;
    }

    /**
     * Function to set ctId
     * 
     * @param ctId
     */
    public void setCtId(String ctId) {
        this.ctId = ctId;
    }

    /**
     * Function to get ctName
     * 
     * @return Component type Name
     */
    @XmlElement(name = "name")
    public EdsWording getCtName() {
        return this.ctName;
    }

    /**
     * Function to set ctName
     * 
     * @param ctName
     */
    public void setCtName(EdsWording ctName) {
        this.ctName = ctName;
    }

    /**
     * Function return local Component type name
     * 
     * @param locale Locale
     * @return local Component type name
     */
    public String getLocaleCtName(Locale locale) {
        return ctName.getValueByLocale(locale);
    }

    /**
     * Function to get Component type Index
     * 
     * @return Component type index
     */
    @XmlTransient
    public int getCtIndex() {
        return ctIndex;
    }

    /**
     * Function to set ctIndex
     * 
     * @param ctIndex Component type index
     */
    public void setCtIndex(int ctIndex) {
        this.ctIndex = ctIndex;
    }

    /**
     * Function to return Component type bttbt ok form set
     * 
     * @return Component type bttbt ok form set
     */
    @XmlTransient
    public String getCtBttbtOkFormset() {
        return this.ctBttbtOkFormset;
    }

    /**
     * Function set Component type bttbt ok form set
     * 
     * @param ctBttbtOkFormset Component type bttbt ok form set
     */
    public void setCtBttbtOkFormset(String ctBttbtOkFormset) {
        this.ctBttbtOkFormset = ctBttbtOkFormset;

        if (ctBttbtOkFormset == null) {
            bttbtFormsSet = Collections.EMPTY_SET;
        } else {
            bttbtFormsSet = new LinkedHashSet<String>(Arrays.asList(ctBttbtOkFormset.split("\\s")));
        }
    }

    /**
     * Function set all Component type bttbt form set
     * 
     * @param bttbtFormsSet
     */
    public void setAllBttbtbForms(Set<String> bttbtFormsSet) {
        if (bttbtFormsSet != null) {
            this.bttbtFormsSet = bttbtFormsSet;

            if (bttbtFormsSet.isEmpty()) {
                this.ctBttbtOkFormset = null;
            } else {
                StringBuilder builder = new StringBuilder();
                for (String formId : bttbtFormsSet) {
                    builder.append(formId);
                    builder.append(' ');
                }
                builder.setLength(builder.length() - 1);

                this.ctBttbtOkFormset = builder.toString();
            }
        }
    }

    /**
     * Function to set bttbtFormsSet
     * 
     * @return set of bttbtform
     */
    @XmlTransient
    public Set<String> getAllBttbtbForms() {
        return bttbtFormsSet;
    }

    /**
     * Function to get ctBttbtKoFormset
     * 
     * @return ctBttbtKoFormset
     */
    @XmlTransient
    public String getCtBttbtKoFormset() {
        return this.ctBttbtKoFormset;
    }

    /**
     * Function to set ctBttbtKoFormset
     * 
     * @param ctBttbtKoFormset
     */
    public void setCtBttbtKoFormset(String ctBttbtKoFormset) {
        this.ctBttbtKoFormset = ctBttbtKoFormset;

        if (ctBttbtKoFormset == null) {
            nonBttbtFormsSet = Collections.EMPTY_SET;
        } else {
            nonBttbtFormsSet = new LinkedHashSet<String>(Arrays.asList(ctBttbtKoFormset.split("\\s")));
        }
    }

    /**
     * Function to set all nonBttbtFormsSet
     * 
     * @param nonBttbtFormsSet
     */
    public void setAllNonBttbtbForms(Set<String> nonBttbtFormsSet) {
        if (nonBttbtFormsSet != null) {
            this.nonBttbtFormsSet = nonBttbtFormsSet;

            if (nonBttbtFormsSet.isEmpty()) {
                this.ctBttbtKoFormset = null;
            } else {
                StringBuilder builder = new StringBuilder();
                for (String formId : nonBttbtFormsSet) {
                    builder.append(formId);
                    builder.append(' ');
                }
                builder.setLength(builder.length() - 1);

                this.ctBttbtKoFormset = builder.toString();
            }
        }
    }

    /**
     * Function to get nonBttbtFormsSet
     * 
     * @return nonBttbtFormsSet
     */
    @XmlTransient
    public Set<String> getAllNonBttbtbForms() {
        return nonBttbtFormsSet;
    }

    /**
     * Function to get edsEdses
     * 
     * @return edsEdses
     */
    @XmlTransient
    public Set getEdsEdses() {
        return this.edsEdses;
    }

    /**
     * Function to set edsEdses
     * 
     * @param edsEdses
     */
    public void setEdsEdses(Set edsEdses) {
        this.edsEdses = edsEdses;
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
        final EdsComponentType other = (EdsComponentType) obj;
        if ((this.ctId == null) ? (other.ctId != null) : !this.ctId.equals(other.ctId)) {
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
        hash = 17 * hash + (this.ctId != null ? this.ctId.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ctName.getValueByLocale(Locale.FRENCH);
    }

}