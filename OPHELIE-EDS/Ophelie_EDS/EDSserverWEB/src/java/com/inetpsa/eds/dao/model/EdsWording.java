package com.inetpsa.eds.dao.model;

// Generated 14 juin 2012 16:43:00 by Hibernate Tools 3.2.1.GA

import java.util.HashMap;
import java.util.Locale;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * EdsWording generated by hbm2java
 */
@XmlType
public class EdsWording implements java.io.Serializable {
    /**
     * Constant to hold value of ALIM
     */
    public final static String ALIM = "alim";
    /**
     * Constant to hold value of ORGAN_FAMILY
     */
    public final static String ORGAN_FAMILY = "organ-family";
    /**
     * Constant to hold value of SSF_ALTERNANTEUR
     */
    public final static String SF_ALTERNANTEUR = "sf_alt";
    /**
     * Constant to hold value of SSF_ALTERNANTEUR
     */
    public final static String SF_BATTERIE = "sf_bat";
    /**
     * Constant to hold value of SSF_ALTERNANTEUR
     */
    public final static String SS_ALTERNANTEUR = "ss_alt";
    /**
     * Constant to hold value of SSF_ALTERNANTEUR
     */
    public final static String SS_BATTERIE = "ss_bat";
    /**
     * Constant to hold value of COMMENT
     */
    public final static String COMMENT = "comment";
    /**
     * Constant to hold value of DATA_ORIGIN
     */
    public final static String DATA_ORIGIN = "data-origin";
    /**
     * Constant to hold value of MILESTONE
     */
    public final static String MILESTONE = "milestone";
    /**
     * Constant to hold value of COMPONENT_TYPE
     */
    public final static String COMPONENT_TYPE = "component-type";
    /**
     * Constant to hold value of DEFAULT_LINKS
     */
    public final static String DEFAULT_LINKS = "default-link";
    /**
     * Constant to hold value of HIDDEN
     */
    public final static int HIDDEN = -1;
    /**
     * Constant to hold value of INACTIVE
     */
    public final static int INACTIVE = 0;
    /**
     * String Variable to hold the value for Column W_ID of Table OPLQTWOR
     */
    private String WId;
    /**
     * String Variable to hold the value for Column W_VALUE of Table OPLQTWOR
     */
    private String WValue;
    /**
     * String Variable to hold the value for Column W_TYPE of Table OPLQTWOR
     */
    private String WType;
    /**
     * int Variable to hold the value for Column W_INDEX of Table OPLQTWOR
     */
    private int WIndex;
    /**
     * HashMap Collection to hold the values for String
     */
    private HashMap<String, String> localeToValueMap;

    /**
     * Default Constructor
     */
    public EdsWording() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param WId Eds wording Id
     * @param WValue Eds wording value
     * @param WType Eds wording type
     * @param WIndex Eds wording index
     */
    public EdsWording(String WId, String WValue, String WType, int WIndex) {
        this.WId = WId;
        this.WValue = WValue;
        this.WType = WType;
        this.WIndex = WIndex;
    }

    /**
     * Function to get WId
     * 
     * @return the Value of WId
     */
    // @XmlTransient
    public String getWId() {
        return this.WId;
    }

    /**
     * Function to set WId
     * 
     * @param WId Eds wording ID
     */
    public void setWId(String WId) {
        this.WId = WId;
    }

    /**
     * Function to get WValue
     * 
     * @return the Value of WValue
     */
    @XmlElement(name = "value")
    public String getWValue() {
        return this.WValue;
    }

    /**
     * Function to set WValue
     * 
     * @param WValue Eds wording value
     */
    public void setWValue(String WValue) {
        this.WValue = WValue;
        this.localeToValueMap = null;
    }

    /**
     * Function to get WType
     * 
     * @return the Value of WType
     */
    // @XmlTransient
    public String getWType() {
        return this.WType;
    }

    /**
     * Function to set WType
     * 
     * @param WType Eds wording type
     */
    public void setWType(String WType) {
        this.WType = WType;
    }

    /**
     * Function to get WIndex
     * 
     * @return the Value of WIndex
     */
    // @XmlTransient
    public int getWIndex() {
        return this.WIndex;
    }

    /**
     * Function to set WIndex
     * 
     * @param WIndex Eds wording index
     */
    public void setWIndex(int WIndex) {
        this.WIndex = WIndex;
    }

    /**
     * Function to get ISO3Language
     * 
     * @param locale Locale
     * @return the ISO3Language
     */
    public String getValueByLocale(Locale locale) {
        String value = getLocaleToValueMap().get(locale.getISO3Language());

        if (value == null) {
            value = getLocaleToValueMap().get(Locale.FRENCH.getISO3Language());
        }

        return value;
    }

    /**
     * This method returns the caption
     * 
     * @return Caption
     */
    public String getCaption() {
        return getLocaleToValueMap().get(Locale.FRENCH.getISO3Language()) + " (" + getLocaleToValueMap().get(Locale.ENGLISH.getISO3Language()) + ")";
    }

    /**
     * Function to get map of language and Eds wording value
     * 
     * @return the map of language and Eds wording value
     */
    @XmlTransient
    public HashMap<String, String> getLocaleToValueMap() {
        if (this.localeToValueMap == null) {
            this.localeToValueMap = new HashMap<String, String>();

            if (getWValue() != null) {
                for (String entry : getWValue().split(";")) {
                    String[] content = entry.split(":", 2);
                    if (content.length > 1) {
                        localeToValueMap.put(content[0], content[1]);
                    }
                }
            }
        }

        return localeToValueMap;
    }

    /**
     * Set Map of language and Eds wording value as Eds wording value
     * 
     * @param localeToValueMap Map of language and Eds wording value
     */
    public void setLocaleToValueMap(HashMap<String, String> localeToValueMap) {
        this.localeToValueMap = localeToValueMap;

        StringBuilder builder = new StringBuilder();

        for (String key : localeToValueMap.keySet()) {
            if (localeToValueMap.get(key) != null) {
                builder.append(key).append(":").append(localeToValueMap.get(key)).append(";");
            }
        }

        this.WValue = builder.toString();
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
        final EdsWording other = (EdsWording) obj;
        if ((this.WId == null) ? (other.WId != null) : !this.WId.equals(other.WId)) {
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
        int hash = 5;
        hash = 47 * hash + (this.WId != null ? this.WId.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getValueByLocale(Locale.FRENCH);
    }
}