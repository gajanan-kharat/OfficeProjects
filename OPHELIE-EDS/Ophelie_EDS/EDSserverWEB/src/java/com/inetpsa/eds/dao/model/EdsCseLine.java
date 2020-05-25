package com.inetpsa.eds.dao.model;

// Generated 16 oct. 2012 11:37:16 by Hibernate Tools 3.2.1.GA

import java.io.Serializable;
import java.util.UUID;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * EdsCseLine generated by hbm2java
 */
@XmlType
public class EdsCseLine implements Serializable {
    /**
     * String Variable to hold the value for Column CSEL_ID of Table OPLQTCSL
     */
    private String cselId;
    /**
     * Variable to hold the value for Column CSEL_CSE_ID of Table OPLQTCSL
     */
    private EdsCseFormData edsCseFormData;
    /**
     * int Variable to hold the value for Column CSEL_NUMBER of Table OPLQTCSL
     */
    private int cselNumber;
    /**
     * String Variable to hold the value for Column CSEL_DATANAME of Table OPLQTCSL
     */
    private String cselDataname;
    /**
     * Float Variable to hold the value for Column CSEL_LOWER_BOUND of Table OPLQTCSL
     */
    private Float cselLowerBound;
    /**
     * Float Variable to hold the value for Column CSEL_UPPER_BOUND of Table OPLQTCSL
     */
    private Float cselUpperBound;
    /**
     * String Variable to hold the value for Column CSEL_UNIT of Table OPLQTCSL
     */
    private String cselUnit;
    /**
     * String Variable to hold the value for Column CSEL_COMMENT of Table OPLQTCSL
     */
    private String cselComment;

    /**
     * Default Constructor
     */
    public EdsCseLine() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param line EdsCseLine
     * @param newFormData EdsCseFormData
     */
    public EdsCseLine(EdsCseLine line, EdsCseFormData newFormData) {
        this(UUID.randomUUID().toString(), newFormData, line.cselNumber, line.cselDataname, line.cselLowerBound, line.cselUpperBound, line.cselUnit,
                line.cselComment);
    }

    /**
     * Parameterized Constructor
     * 
     * @param cselId Id
     * @param edsCseFormData EdsCseFormData
     * @param cselNumber Cse line number
     * @param cselDataname Cse line data name
     */
    public EdsCseLine(String cselId, EdsCseFormData edsCseFormData, int cselNumber, String cselDataname) {
        this.cselId = cselId;
        this.edsCseFormData = edsCseFormData;
        this.cselNumber = cselNumber;
        this.cselDataname = cselDataname;
    }

    /**
     * Parameterized Constructor
     * 
     * @param cselId Id
     * @param edsCseFormData EdsCseFormData
     * @param cselNumber cse line number
     * @param cselDataname Cse data name
     * @param cselLowerBound cse lower bound
     * @param cselUpperBound cse upper bound
     * @param cselUnit cse Unit
     * @param cselComment Comment
     */
    public EdsCseLine(String cselId, EdsCseFormData edsCseFormData, int cselNumber, String cselDataname, Float cselLowerBound, Float cselUpperBound,
            String cselUnit, String cselComment) {
        this.cselId = cselId;
        this.edsCseFormData = edsCseFormData;
        this.cselNumber = cselNumber;
        this.cselDataname = cselDataname;
        this.cselLowerBound = cselLowerBound;
        this.cselUpperBound = cselUpperBound;
        this.cselUnit = cselUnit;
        this.cselComment = cselComment;
    }

    /**
     * Function to get cselId
     * 
     * @return Id
     */
    @XmlTransient
    public String getCselId() {
        return this.cselId;
    }

    /**
     * Function to set cselId
     * 
     * @param cselId Id
     */
    public void setCselId(String cselId) {
        this.cselId = cselId;
    }

    /**
     * Function to get edsCseFormData
     * 
     * @return EdsCseFormData
     */
    @XmlTransient
    public EdsCseFormData getEdsCseFormData() {
        return this.edsCseFormData;
    }

    /**
     * Function to set edsCseFormData
     * 
     * @param edsCseFormData EdsCseFormData
     */
    public void setEdsCseFormData(EdsCseFormData edsCseFormData) {
        this.edsCseFormData = edsCseFormData;
    }

    /**
     * Function to get cselNumber
     * 
     * @return cse Line number
     */
    @XmlElement(name = "number")
    public int getCselNumber() {
        return this.cselNumber;
    }

    /**
     * Function to set cselNumber
     * 
     * @param cselNumber cse line number
     */
    public void setCselNumber(int cselNumber) {
        this.cselNumber = cselNumber;
    }

    /**
     * Function to get cselDataname
     * 
     * @return Cse data name
     */
    @XmlElement(name = "data-name")
    public String getCselDataname() {
        return this.cselDataname;
    }

    /**
     * Function to set cselDataname
     * 
     * @param cselDataname Cse data name
     */
    public void setCselDataname(String cselDataname) {
        this.cselDataname = cselDataname;
    }

    /**
     * Function to get cselLowerBound
     * 
     * @return Cse lower bound
     */
    @XmlElement(name = "lower-bound")
    public Float getCselLowerBound() {
        return this.cselLowerBound;
    }

    /**
     * Function to set cselLowerBound
     * 
     * @param cselLowerBound Cse lower bound
     */
    public void setCselLowerBound(Float cselLowerBound) {
        this.cselLowerBound = cselLowerBound;
    }

    /**
     * Function to get cselUpperBound
     * 
     * @return Cse upper bound
     */
    @XmlElement(name = "upper-bound")
    public Float getCselUpperBound() {
        return this.cselUpperBound;
    }

    /**
     * Function to set cselUpperBound
     * 
     * @param cselUpperBound Cse upper bound
     */
    public void setCselUpperBound(Float cselUpperBound) {
        this.cselUpperBound = cselUpperBound;
    }

    /**
     * Function to get cselUnit
     * 
     * @return Cse unit
     */
    @XmlElement(name = "unit")
    public String getCselUnit() {
        return this.cselUnit;
    }

    /**
     * Function to set cselUnit
     * 
     * @param cselUnit Cse Unit
     */
    public void setCselUnit(String cselUnit) {
        this.cselUnit = cselUnit;
    }

    /**
     * Function to get cselComment
     * 
     * @return Cse comment
     */
    @XmlElement(name = "comment")
    public String getCselComment() {
        return this.cselComment;
    }

    /**
     * Function to set cselComment
     * 
     * @param cselComment Cse comment
     */
    public void setCselComment(String cselComment) {
        this.cselComment = cselComment;
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
        final EdsCseLine other = (EdsCseLine) obj;
        if ((this.cselId == null) ? (other.cselId != null) : !this.cselId.equals(other.cselId)) {
            return false;
        }
        if (this.cselNumber != other.cselNumber) {
            return false;
        }
        if ((this.cselDataname == null) ? (other.cselDataname != null) : !this.cselDataname.equals(other.cselDataname)) {
            return false;
        }
        if ((this.cselLowerBound == null) ? (other.cselLowerBound != null) : !this.cselLowerBound.equals(other.cselLowerBound)) {
            return false;
        }
        if ((this.cselUpperBound == null) ? (other.cselUpperBound != null) : !this.cselUpperBound.equals(other.cselUpperBound)) {
            return false;
        }
        if ((this.cselUnit == null) ? (other.cselUnit != null) : !this.cselUnit.equals(other.cselUnit)) {
            return false;
        }
        if ((this.cselComment == null) ? (other.cselComment != null) : !this.cselComment.equals(other.cselComment)) {
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
        hash = 97 * hash + (this.cselId != null ? this.cselId.hashCode() : 0);
        hash = 97 * hash + this.cselNumber;
        hash = 97 * hash + (this.cselDataname != null ? this.cselDataname.hashCode() : 0);
        hash = 97 * hash + (this.cselLowerBound != null ? this.cselLowerBound.hashCode() : 0);
        hash = 97 * hash + (this.cselUpperBound != null ? this.cselUpperBound.hashCode() : 0);
        hash = 97 * hash + (this.cselUnit != null ? this.cselUnit.hashCode() : 0);
        hash = 97 * hash + (this.cselComment != null ? this.cselComment.hashCode() : 0);
        return hash;
    }
}