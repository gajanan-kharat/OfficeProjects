/*
 * Creation : 21 mai 2015
 */
package com.inetpsa.eds.dao.model;

import java.util.UUID;

public class ConsolidateSupplyEdsTension implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // PRIVATE FIELDS
    /**
     * String Variable to hold the value for Column CSEDSU_ID of Table OPLQTMST
     */
    private String csEdsUId;
    /**
     * String Variable to hold the value for Column CSEDSU_NAME of Table OPLQTMST
     */
    private String OperatingModeName;
    /**
     * Float variable to hold value of Column CSEDST_UMIN of Table EDS_CONSOLIDATED_SUPPLY_THEORITIC
     */
    private Float csEdsUmin;

    /**
     * Float variable to hold value of Column CSEDST_UMOY of Table EDS_CONSOLIDATED_SUPPLY_THEORITIC
     */
    private Float csEdsUmoy;

    /**
     * Float variable to hold value of Column CSEDST_UMAX of Table EDS_CONSOLIDATED_SUPPLY_THEORITIC
     */
    private Float csEdsUmax;

    /**
     * String Variable to hold the value for Column CSEDS_UMIN_COMMENT of Table EDS_CONSOLIDATED_SUPPLY_THEORITIC
     */
    private String csEdsUminComment;
    /**
     * String Variable to hold the value for Column CSEDS_UMOY_COMMENT of Table EDS_CONSOLIDATED_SUPPLY_THEORITIC
     */
    private String csEdsUmoyComment;
    /**
     * String Variable to hold the value for Column CSEDS_UMAX_COMMENT of Table EDS_CONSOLIDATED_SUPPLY_THEORITIC
     */
    private String csEdsUmaxComment;

    /**
     * Default Constructor: Can't use this default constructor because of the constraint edsConsolidateSupplyTheoritic Not Null.
     */
    public ConsolidateSupplyEdsTension() {
        this.csEdsUId = UUID.randomUUID().toString();
    }

    /**
     * Parameterized Constructor
     * 
     * @param csEdsU
     */
    public ConsolidateSupplyEdsTension(ConsolidateSupplyEdsTension csEdsU) {
        this();
        this.OperatingModeName = csEdsU.OperatingModeName;
        this.csEdsUmin = csEdsU.csEdsUmin;
        this.csEdsUmoy = csEdsU.csEdsUmoy;
        this.csEdsUmax = csEdsU.csEdsUmax;
        this.csEdsUminComment = csEdsU.csEdsUminComment;
        this.csEdsUmoyComment = csEdsU.csEdsUmoyComment;
        this.csEdsUmaxComment = csEdsU.csEdsUmaxComment;

    }

    /**
     * Getter csEdsUmin
     * 
     * @return the csEdsUmin
     */
    public Float getCsEdsUmin() {
        return csEdsUmin;
    }

    /**
     * Setter csEdsUmin
     * 
     * @param csEdsUmin the csEdsUmin to set
     */
    public void setCsEdsUmin(Float csEdsUmin) {
        this.csEdsUmin = csEdsUmin;
    }

    /**
     * Getter csEdsUmoy
     * 
     * @return the csEdsUmoy
     */
    public Float getCsEdsUmoy() {
        return csEdsUmoy;
    }

    /**
     * Setter csEdsUmoy
     * 
     * @param csEdsUmoy the csEdsUmoy to set
     */
    public void setCsEdsUmoy(Float csEdsUmoy) {
        this.csEdsUmoy = csEdsUmoy;
    }

    /**
     * Getter csEdsUId
     * 
     * @return the csEdsUId
     */
    public String getCsEdsUId() {
        return csEdsUId;
    }

    /**
     * Setter csEdsUId
     * 
     * @param csEdsUId the csEdsUId to set
     */
    public void setCsEdsUId(String csEdsUId) {
        this.csEdsUId = csEdsUId;
    }

    /**
     * Getter csEdsUmax
     * 
     * @return the csEdsUmax
     */
    public Float getCsEdsUmax() {
        return csEdsUmax;
    }

    /**
     * Setter csEdsUmax
     * 
     * @param csEdsUmax the csEdsUmax to set
     */
    public void setCsEdsUmax(Float csEdsUmax) {
        this.csEdsUmax = csEdsUmax;
    }

    /**
     * Getter csEdsUminComment
     * 
     * @return the csEdsUminComment
     */
    public String getCsEdsUminComment() {
        return csEdsUminComment;
    }

    /**
     * Setter csEdsUminComment
     * 
     * @param csEdsUminComment the csEdsUminComment to set
     */
    public void setCsEdsUminComment(String csEdsUminComment) {
        this.csEdsUminComment = csEdsUminComment;
    }

    /**
     * Getter csEdsUmoyComment
     * 
     * @return the csEdsUmoyComment
     */
    public String getCsEdsUmoyComment() {
        return csEdsUmoyComment;
    }

    /**
     * Setter csEdsUmoyComment
     * 
     * @param csEdsUmoyComment the csEdsUmoyComment to set
     */
    public void setCsEdsUmoyComment(String csEdsUmoyComment) {
        this.csEdsUmoyComment = csEdsUmoyComment;
    }

    /**
     * Getter csEdsUmaxComment
     * 
     * @return the csEdsUmaxComment
     */
    public String getCsEdsUmaxComment() {
        return csEdsUmaxComment;
    }

    /**
     * Setter csEdsUmaxComment
     * 
     * @param csEdsUmaxComment the csEdsUmaxComment to set
     */
    public void setCsEdsUmaxComment(String csEdsUmaxComment) {
        this.csEdsUmaxComment = csEdsUmaxComment;
    }

    /**
     * Getter operatingModeName
     * 
     * @return the operatingModeName
     */
    public String getOperatingModeName() {
        return OperatingModeName;
    }

    /**
     * Setter operatingModeName
     * 
     * @param operatingModeName the operatingModeName to set
     */
    public void setOperatingModeName(String operatingModeName) {
        OperatingModeName = operatingModeName;
    }

}
