package com.inetpsa.poc00.rest.projectcodefamily;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;

/**
 * The Class ProjectCodeFamilyRepresentation.
 */
public class ProjectCodeFamilyRepresentation {

    /** The rule id. */
    private Long ruleId;

    /** The project code label. */
    private String projectCodeLabel;

    /** The vehicle family label. */
    private String vehicleFamilyLabel;

    /** The famille boc. */
    private String familleBOC;

    /** The famille kmat. */
    private String familleKmat;

    /** The famille r lable. */
    private String familleRLable;

    /** The famille tvv rule id. */
    private Long familleTvvRuleId;

    /** The display label. */
    private String displayLabel;

    /** The tvv rule id. */
    @JsonIgnore
    private GenomeTVVRule tvvRuleId;

    /** The entity id. */
    private Long entityId;

    /**
     * Instantiates a new project code family representation.
     * 
     * @param lable the lable
     * @param kmat the kmat
     * @param frLabel the fR lable
     * @param projectCodeLabel the project code label
     * @param vehicleFamilyLabel the vehicle family label
     * @param familleTvvRuleId the famille tvv rule id
     * @param entityId the entity id
     */
    public ProjectCodeFamilyRepresentation(String lable, String kmat, String frLabel, String projectCodeLabel, String vehicleFamilyLabel,
            Long familleTvvRuleId, Long entityId) {
        this.familleBOC = lable;
        this.familleKmat = kmat;
        this.familleRLable = frLabel;
        this.projectCodeLabel = projectCodeLabel;
        this.vehicleFamilyLabel = vehicleFamilyLabel;
        this.familleTvvRuleId = familleTvvRuleId;
        this.entityId = entityId;
        this.displayLabel = this.projectCodeLabel + "-" + this.vehicleFamilyLabel;
    }

    /**
     * Instantiates a new project code family representation.
     * 
     * @param entityId the entity id
     */
    public ProjectCodeFamilyRepresentation(Long entityId) {

        this.entityId = entityId;
    }

    /**
     * Instantiates a new project code family representation.
     *
     * @param entityId the entity id
     * @param projectCodeLabel the project code label
     * @param vehicleFamilyLabel the vehicle family label
     * @param kmat the kmat
     */
    public ProjectCodeFamilyRepresentation(Long entityId, String projectCodeLabel, String vehicleFamilyLabel, String kmat) {

        this.projectCodeLabel = projectCodeLabel;
        this.vehicleFamilyLabel = vehicleFamilyLabel;
        this.displayLabel = kmat + "-" + this.projectCodeLabel + "-" + this.vehicleFamilyLabel;
        this.setEntityId(entityId);
    }

    /**
     * Instantiates a new project code family representation.
     *
     * @param entityId the entity id
     * @param projectCodeLabel the project code label
     * @param vehicleFamilyLabel the vehicle family label
     */
    public ProjectCodeFamilyRepresentation(Long entityId, String projectCodeLabel, String vehicleFamilyLabel) {
        this.projectCodeLabel = projectCodeLabel;
        this.vehicleFamilyLabel = vehicleFamilyLabel;
        this.displayLabel = this.projectCodeLabel + "-" + this.vehicleFamilyLabel;
        this.setEntityId(entityId);
    }

    /**
     * Instantiates a new project code family representation.
     */
    public ProjectCodeFamilyRepresentation() {
        // Default Constructor
    }

    /**
     * Gets the rule id.
     * 
     * @return the rule id
     */
    public Long getRuleId() {
        return ruleId;
    }

    /**
     * Sets the rule id.
     * 
     * @param ruleId the new rule id
     */
    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * Gets the project code label.
     * 
     * @return the project code label
     */
    public String getProjectCodeLabel() {
        return projectCodeLabel;
    }

    /**
     * Sets the project code label.
     * 
     * @param projectCodeLabel the new project code label
     */
    public void setProjectCodeLabel(String projectCodeLabel) {
        this.projectCodeLabel = projectCodeLabel;
    }

    /**
     * Gets the vehicle family label.
     * 
     * @return the vehicle family label
     */
    public String getVehicleFamilyLabel() {
        return vehicleFamilyLabel;
    }

    /**
     * Sets the vehicle family label.
     * 
     * @param vehicleFamilyLabel the new vehicle family label
     */
    public void setVehicleFamilyLabel(String vehicleFamilyLabel) {
        this.vehicleFamilyLabel = vehicleFamilyLabel;
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
     * Gets the famille boc.
     * 
     * @return the famille boc
     */
    public String getFamilleBOC() {
        return familleBOC;
    }

    /**
     * Sets the famille boc.
     * 
     * @param familleBOC the new famille boc
     */
    public void setFamilleBOC(String familleBOC) {
        this.familleBOC = familleBOC;
    }

    /**
     * Gets the famille kmat.
     * 
     * @return the famille kmat
     */
    public String getFamilleKmat() {
        return familleKmat;
    }

    /**
     * Sets the famille kmat.
     * 
     * @param familleKmat the new famille kmat
     */
    public void setFamilleKmat(String familleKmat) {
        this.familleKmat = familleKmat;
    }

    /**
     * Gets the famille r lable.
     * 
     * @return the famille r lable
     */
    public String getFamilleRLable() {
        return familleRLable;
    }

    /**
     * Sets the famille r lable.
     * 
     * @param familleRLable the new famille r lable
     */
    public void setFamilleRLable(String familleRLable) {
        this.familleRLable = familleRLable;
    }

    /**
     * Gets the famille tvv rule id.
     * 
     * @return the famille tvv rule id
     */
    public Long getFamilleTvvRuleId() {
        return familleTvvRuleId;
    }

    /**
     * Sets the famille tvv rule id.
     * 
     * @param familleTvvRuleId the new famille tvv rule id
     */
    public void setFamilleTvvRuleId(Long familleTvvRuleId) {
        this.familleTvvRuleId = familleTvvRuleId;
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
