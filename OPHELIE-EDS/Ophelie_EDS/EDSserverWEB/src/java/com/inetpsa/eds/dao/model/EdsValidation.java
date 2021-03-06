package com.inetpsa.eds.dao.model;

// Generated 26 sept. 2012 11:19:38 by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.UUID;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * EdsValidation generated by hbm2java
 */
@XmlType
public class EdsValidation implements java.io.Serializable {
    /**
     * Constant to hold value of VALIDATION_NONE
     */
    public static final int VALIDATION_NONE = 0;
    /**
     * Constant to hold value of VALIDATION_STRICT_NOK
     */
    public static final int VALIDATION_STRICT_NOK = 1;
    /**
     * Constant to hold value of VALIDATION_WEAK_NOK
     */
    public static final int VALIDATION_WEAK_NOK = 2;
    /**
     * Constant to hold value of VALIDATION_OK
     */
    public static final int VALIDATION_OK = 3;
    /**
     * Constant to hold value of array of String
     */
    private static final String[] VALIDATION_TEXTS = new String[] { "N/A", "NOK bloquant", "NOK gênant", "OK" };
    /**
     * String Variable to hold the value for Column V_ID of Table OPLQTVAL
     */
    private String VId;
    /**
     * Variable to hold the value for Column V_COMMENT1_ID of Table OPLQTVAL
     */
    private EdsWording edsWording;
    /**
     * Variable to hold the value for Column V_VALIDATOR_ID of Table OPLQTVAL
     */
    private EdsUser edsUser;
    /**
     * int Variable to hold the value for Column V_STATUS of Table OPLQTVAL
     */
    private int VStatus;
    /**
     * Date Variable to hold the value for Column V_VALIDATION_DATE of Table OPLQTVAL
     */
    private Date VValidationDate;
    /**
     * String Variable to hold the value for Column V_COMMENT2 of Table OPLQTVAL
     */
    private String VComment2;

    /**
     * Default Constructor
     */
    public EdsValidation() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param VId Eds Validation Id
     * @param VStatus Eds Validation status
     */
    public EdsValidation(String VId, int VStatus) {
        this.VId = VId;
        this.VStatus = VStatus;
    }

    /**
     * Parameterized Constructor
     * 
     * @param VId Eds Validation Id
     * @param edsWording Eds Wording
     * @param edsUser Eds USer
     * @param VStatus Eds Validation status
     * @param VValidationDate Eds Validation date
     * @param VComment2 Eds Validation comment
     */
    public EdsValidation(String VId, EdsWording edsWording, EdsUser edsUser, int VStatus, Date VValidationDate, String VComment2) {
        this.VId = VId;
        this.edsWording = edsWording;
        this.edsUser = edsUser;
        this.VStatus = VStatus;
        this.VValidationDate = VValidationDate;
        this.VComment2 = VComment2;
    }

    /**
     * Parameterized Constructor
     * 
     * @param otherValidation Eds Validation
     */
    public EdsValidation(EdsValidation otherValidation) {
        this(UUID.randomUUID().toString(), otherValidation.getEdsWording(), otherValidation.getEdsUser(), otherValidation.getVStatus(),
                otherValidation.getVValidationDate(), otherValidation.getVComment2());
    }

    /**
     * Function to get VId
     * 
     * @return the Value of VId
     */
    @XmlTransient
    public String getVId() {
        return this.VId;
    }

    /**
     * Function to set VId
     * 
     * @param VId Eds Validation Id
     */
    public void setVId(String VId) {
        this.VId = VId;
    }

    /**
     * Function to get edsWording
     * 
     * @return the Value of edsWording
     */
    @XmlElement(name = "comments")
    public EdsWording getEdsWording() {
        return this.edsWording;
    }

    /**
     * Function to set edsWording
     * 
     * @param edsWording Eds wording
     */
    public void setEdsWording(EdsWording edsWording) {
        this.edsWording = edsWording;
    }

    /**
     * Function to get edsUser
     * 
     * @return the Value of edsUser
     */
    @XmlElement(name = "validator")
    public EdsUser getEdsUser() {
        return this.edsUser;
    }

    /**
     * Function to set edsUser
     * 
     * @param edsUser Eds User
     */
    public void setEdsUser(EdsUser edsUser) {
        this.edsUser = edsUser;
    }

    /**
     * Function to get VStatus
     * 
     * @return the Value of VStatus
     */
    @XmlElement(name = "status")
    public int getVStatus() {
        return this.VStatus;
    }

    /**
     * Function to set VStatus
     * 
     * @param VStatus Eds Validation status
     */
    public void setVStatus(int VStatus) {
        this.VStatus = VStatus;
    }

    /**
     * Function to get VValidationDate
     * 
     * @return the Value of VValidationDate
     */
    @XmlElement(name = "validation-date")
    public Date getVValidationDate() {
        return this.VValidationDate;
    }

    /**
     * Function to set VValidationDate
     * 
     * @param VValidationDate Eds Validation Date
     */
    public void setVValidationDate(Date VValidationDate) {
        this.VValidationDate = VValidationDate;
    }

    /**
     * Function to get VComment2
     * 
     * @return the Value of VComment2
     */
    @XmlElement(name = "comments-2")
    public String getVComment2() {
        return this.VComment2;
    }

    /**
     * Function to set VComment2
     * 
     * @param VComment2 Eds Validation comment
     */
    public void setVComment2(String VComment2) {
        this.VComment2 = VComment2;
    }

    /**
     * Function to get VALIDATION_TEXTS
     * 
     * @param validationStatus Eds Validation status
     * @return VALIDATION_TEXTS of validationStatus
     */
    public static String getValidationText(int validationStatus) {
        return VALIDATION_TEXTS[validationStatus];
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
        final EdsValidation other = (EdsValidation) obj;
        if ((this.VId == null) ? (other.VId != null) : !this.VId.equals(other.VId)) {
            return false;
        }
        if (this.edsWording.getWId() != other.edsWording.getWId()
                && (this.edsWording.getWId() == null || !this.edsWording.getWId().equals(other.edsWording.getWId()))) {
            return false;
        }
        if (this.edsUser.getUId() != other.edsUser.getUId()
                && (this.edsUser.getUId() == null || !this.edsUser.getUId().equals(other.edsUser.getUId()))) {
            return false;
        }
        if (this.VStatus != other.VStatus) {
            return false;
        }
        if (this.VValidationDate != other.VValidationDate && (this.VValidationDate == null || !this.VValidationDate.equals(other.VValidationDate))) {
            return false;
        }
        if ((this.VComment2 == null) ? (other.VComment2 != null) : !this.VComment2.equals(other.VComment2)) {
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
        hash = 31 * hash + (this.VId != null ? this.VId.hashCode() : 0);
        hash = 31 * hash + (this.edsWording != null ? this.edsWording.getWId().hashCode() : 0);
        hash = 31 * hash + (this.edsUser != null ? this.edsUser.getUId().hashCode() : 0);
        hash = 31 * hash + this.VStatus;
        hash = 31 * hash + (this.VValidationDate != null ? this.VValidationDate.hashCode() : 0);
        hash = 31 * hash + (this.VComment2 != null ? this.VComment2.hashCode() : 0);
        return hash;
    }
}
