package com.inetpsa.poc00.rest.carbrand;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;

/**
 * The Class CarBrandRepresentation.
 */
public class CarBrandRepresentation {

    /** The rule ID. */
    private Long ruleID;

    /** The entity id. */
    private Long entityId;

    /** The Maker lable. */
    private String makerLable;

    /** The Brand lable. */
    private String brandLable;

    /** The constructeur BOB. */
    private String constructeurBOB;

    /** The constructeur kmat. */
    private String constructeurKmat;

    /** The constructeur FR lable. */
    private String constructeurFRLable;

    /** The constructeur tvv rule id. */
    private Long constructeurTvvRuleId;

    /** The constructeur brand label. */
    private String constructeurBrandLabel;

    /** The constructeur maker label. */
    private String constructeurMakerLabel;

    /** The tvv rule id. */
    @JsonIgnore
    private GenomeTVVRule tvvRuleId;

    /** The display label. */
    private String displayLabel;

    /**
     * Instantiates a new car brand representation.
     * 
     * @param tvvRuleId the tvv rule id
     * @param lable the lable
     * @param kmat the kmat
     * @param frLable the FR lable
     * @param brandLabel the brand label
     * @param makerLabel the maker label
     * @param entityId the entity id
     */
    public CarBrandRepresentation(Long tvvRuleId, String lable, String kmat, String frLable, String brandLabel, String makerLabel, Long entityId) {

        this.constructeurBOB = lable;
        this.constructeurKmat = kmat;
        this.constructeurFRLable = frLable;
        this.constructeurTvvRuleId = tvvRuleId;
        this.constructeurBrandLabel = brandLabel;
        this.constructeurMakerLabel = makerLabel;
        this.displayLabel = this.constructeurBrandLabel + "-" + this.constructeurMakerLabel;
        this.entityId = entityId;
    }

    /**
     * Instantiates a new car brand representation.
     * 
     * @param entityId the entity id
     */
    public CarBrandRepresentation(Long entityId) {

        this.entityId = entityId;

    }

    /**
     * Instantiates a new car brand representation.
     * 
     * @param entityId the entity id
     * @param brandLabel the brand label
     * @param makerLabel the maker label
     */
    public CarBrandRepresentation(Long entityId, String brandLabel, String makerLabel, String b0BValue) {

        this.entityId = entityId;
        this.brandLable = brandLabel;
        this.makerLable = makerLabel;
        this.displayLabel = b0BValue + "-" + this.brandLable + "-" + this.makerLable;
    }

    /**
     * Instantiates a new car brand representation.
     * 
     * @param entityId the entity id
     * @param brandLabel the brand label
     * @param makerLabel the maker label
     */
    public CarBrandRepresentation(Long entityId, String brandLabel, String makerLabel) {

        this.entityId = entityId;
        this.brandLable = brandLabel;
        this.makerLable = makerLabel;
        this.displayLabel = this.brandLable + "-" + this.makerLable;
    }

    /**
     * Instantiates a new car brand representation.
     */
    public CarBrandRepresentation() {
        // Default Constructor
    }

    /**
     * Gets the rule ID.
     * 
     * @return the rule ID
     */
    public Long getRuleID() {
        return ruleID;
    }

    /**
     * Sets the rule ID.
     * 
     * @param ruleID the new rule ID
     */
    public void setRuleID(Long ruleID) {
        this.ruleID = ruleID;
    }

    /**
     * Gets the maker lable.
     * 
     * @return the maker lable
     */
    public String getMakerLable() {
        return makerLable;
    }

    /**
     * Sets the maker lable.
     * 
     * @param makerLable the new maker lable
     */
    public void setMakerLable(String makerLable) {
        this.makerLable = makerLable;
    }

    /**
     * Gets the brand lable.
     * 
     * @return the brand lable
     */
    public String getBrandLable() {
        return brandLable;
    }

    /**
     * Sets the brand lable.
     * 
     * @param brandLable the new brand lable
     */
    public void setBrandLable(String brandLable) {
        this.brandLable = brandLable;
    }

    /**
     * Gets the tvv rule id.
     * 
     * @return the tvv rule id
     */
    public GenomeTVVRule getTvvRuleId() {
        return tvvRuleId;
    }

    /**
     * Sets the tvv rule id.
     * 
     * @param tvvRuleId the new tvv rule id
     */
    public void setTvvRuleId(GenomeTVVRule tvvRuleId) {
        this.tvvRuleId = tvvRuleId;
    }

    /**
     * Gets the constructeur BOB.
     * 
     * @return the constructeur BOB
     */
    public String getConstructeurBOB() {
        return constructeurBOB;
    }

    /**
     * Sets the constructeur BOB.
     * 
     * @param constructeurBOB the new constructeur BOB
     */
    public void setConstructeurBOB(String constructeurBOB) {
        this.constructeurBOB = constructeurBOB;
    }

    /**
     * Gets the constructeur kmat.
     * 
     * @return the constructeur kmat
     */
    public String getConstructeurKmat() {
        return constructeurKmat;
    }

    /**
     * Sets the constructeur kmat.
     * 
     * @param constructeurKmat the new constructeur kmat
     */
    public void setConstructeurKmat(String constructeurKmat) {
        this.constructeurKmat = constructeurKmat;
    }

    /**
     * Gets the constructeur FR lable.
     * 
     * @return the constructeur FR lable
     */
    public String getConstructeurFRLable() {
        return constructeurFRLable;
    }

    /**
     * Sets the constructeur FR lable.
     * 
     * @param constructeurFRLable the new constructeur FR lable
     */
    public void setConstructeurFRLable(String constructeurFRLable) {
        this.constructeurFRLable = constructeurFRLable;
    }

    /**
     * Gets the constructeur tvv rule id.
     * 
     * @return the constructeur tvv rule id
     */
    public Long getConstructeurTvvRuleId() {
        return constructeurTvvRuleId;
    }

    /**
     * Sets the constructeur tvv rule id.
     * 
     * @param constructeurTvvRuleId the new constructeur tvv rule id
     */
    public void setConstructeurTvvRuleId(Long constructeurTvvRuleId) {
        this.constructeurTvvRuleId = constructeurTvvRuleId;
    }

    /**
     * Gets the constructeur brand label.
     * 
     * @return the constructeur brand label
     */
    public String getConstructeurBrandLabel() {
        return constructeurBrandLabel;
    }

    /**
     * Sets the constructeur brand label.
     * 
     * @param constructeurBrandLabel the new constructeur brand label
     */
    public void setConstructeurBrandLabel(String constructeurBrandLabel) {
        this.constructeurBrandLabel = constructeurBrandLabel;
    }

    /**
     * Gets the constructeur maker label.
     * 
     * @return the constructeur maker label
     */
    public String getConstructeurMakerLabel() {
        return constructeurMakerLabel;
    }

    /**
     * Sets the constructeur maker label.
     * 
     * @param constructeurMakerLabel the new constructeur maker label
     */
    public void setConstructeurMakerLabel(String constructeurMakerLabel) {
        this.constructeurMakerLabel = constructeurMakerLabel;
    }

    /**
     * Gets the entity id.
     * 
     * @return the entity id
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Sets the entity id.
     * 
     * @param entityId the new entity id
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Gets the display label.
     * 
     * @return the display label
     */
    public String getDisplayLabel() {
        return displayLabel;
    }

    /**
     * Sets the display label.
     * 
     * @param displayLabel the new display label
     */
    public void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
    }

}
