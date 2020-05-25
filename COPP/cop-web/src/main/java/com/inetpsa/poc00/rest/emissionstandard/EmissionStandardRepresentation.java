/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.rest.emissionstandard;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentation;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;

/**
 * The Class EmissionStandardRepresentation.
 */
public class EmissionStandardRepresentation extends BaseRepresentation {

    /** The entity id. */
    private Long entityId;

    /** The description. */
    private String description;

    /** The version. */
    private String version;

    /** The es label. */
    private String esLabel;

    /** The vehicle technology set. */

    private List<VehicleTechnologyRepresentation> vehicleTechnologySet = new ArrayList<VehicleTechnologyRepresentation>();

    /** The fuel injection types. */

    private List<FuelInjectionTypeRepresentation> fuelInjectionTypes = new ArrayList<FuelInjectionTypeRepresentation>();

    /** The categories. */

    private List<CategoryRepresentation> categories = new ArrayList<CategoryRepresentation>();

    /** The fuels. */

    private List<FuelRepresentation> fuels = new ArrayList<FuelRepresentation>();

    /** The status. */
    private StatusRepresentation status;

    /** The rule id. */
    private Long ruleId;

    /** The es description. */
    private String esDescription;

    /** The Reglementation dka. */
    private String reglementationDKA;

    /** The Reglementation kmat. */
    private String reglementationKmat;

    /** The Reglementation fr lable. */
    private String reglementationFRLable;

    /** The Reglementation tvv rule id. */
    private Long reglementationTvvRuleId;

    /** The tvv rule id. */
    @JsonIgnore
    private GenomeTVVRule tvvRuleId;

    /** The display label. */
    private String displayLabel;

    /** The display label tvv. */
    private String displayLabelTVV;

    /**
     * Instantiates a new emission standard representation.
     * 
     * @param lable the lable
     * @param kmat the kmat
     * @param frLabel the fR lable
     * @param tvvRuleId the tvv rule id
     * @param esLabel the es label
     * @param esDescription the es description
     * @param entityId the entity id
     */
    public EmissionStandardRepresentation(String lable, String kmat, String frLabel, Long tvvRuleId, String esLabel, String esDescription,
            Long entityId) {
        this.reglementationDKA = lable;
        this.reglementationKmat = kmat;
        this.reglementationFRLable = frLabel;
        this.reglementationTvvRuleId = tvvRuleId;
        if (esLabel == null) {
            esLabel = frLabel;
        }
        this.esLabel = esLabel;
        this.description = esDescription;
        this.entityId = entityId;
    }

    /**
     * Instantiates a new emission standard representation.
     * 
     * @param entityId the entity id
     * @param description the description
     * @param version the version
     * @param esLabel the es label
     * @param statusentityId the statusentity id
     * @param statuslabel the statuslabel
     * @param statusguiLabel the statusgui label
     */
    public EmissionStandardRepresentation(long entityId, String description, String version, String esLabel, long statusentityId, String statuslabel,
            String statusguiLabel) {
        this.entityId = entityId;
        this.description = description;
        this.version = version;

        this.esLabel = esLabel;
        this.status = new StatusRepresentation(statusentityId, statuslabel, statusguiLabel);
        this.displayLabel = esLabel + " " + statusguiLabel + " V" + version;
        this.setDisplayLabelTVV(esLabel + " " + version);
    }

    /**
     * Instantiates a new emission standard representation.
     * 
     * @param entityId the entity id
     */
    public EmissionStandardRepresentation(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Instantiates a new emission standard representation.
     *
     * @param esLabel the es label
     * @param version the version
     */
    public EmissionStandardRepresentation(String esLabel, String version) {
        this.esLabel = esLabel;
        this.version = version;
    }

    /**
     * Instantiates a new emission standard representation.
     */
    public EmissionStandardRepresentation() {
    	super();
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
     * Gets the es description.
     * 
     * @return the es description
     */
    public String getEsDescription() {
        return esDescription;
    }

    /**
     * Sets the es description.
     * 
     * @param esDescription the new es description
     */
    public void setEsDescription(String esDescription) {
        this.esDescription = esDescription;
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
     * Gets the reglementation dka.
     * 
     * @return the reglementation dka
     */
    public String getReglementationDKA() {
        return reglementationDKA;
    }

    /**
     * Sets the reglementation dka.
     * 
     * @param reglementationDKA the new reglementation dka
     */
    public void setReglementationDKA(String reglementationDKA) {
        this.reglementationDKA = reglementationDKA;
    }

    /**
     * Gets the reglementation kmat.
     * 
     * @return the reglementation kmat
     */
    public String getReglementationKmat() {
        return reglementationKmat;
    }

    /**
     * Sets the reglementation kmat.
     * 
     * @param reglementationKmat the new reglementation kmat
     */
    public void setReglementationKmat(String reglementationKmat) {
        this.reglementationKmat = reglementationKmat;
    }

    /**
     * Gets the reglementation fr lable.
     * 
     * @return the reglementation fr lable
     */
    public String getReglementationFRLable() {
        return reglementationFRLable;
    }

    /**
     * Sets the reglementation fr lable.
     * 
     * @param reglementationFRLable the new reglementation fr lable
     */
    public void setReglementationFRLable(String reglementationFRLable) {
        this.reglementationFRLable = reglementationFRLable;
    }

    /**
     * Gets the reglementation tvv rule id.
     * 
     * @return the reglementation tvv rule id
     */
    public Long getReglementationTvvRuleId() {
        return reglementationTvvRuleId;
    }

    /**
     * Sets the reglementation tvv rule id.
     * 
     * @param reglementationTvvRuleId the new reglementation tvv rule id
     */
    public void setReglementationTvvRuleId(Long reglementationTvvRuleId) {
        this.reglementationTvvRuleId = reglementationTvvRuleId;
    }

    /**
     * Instantiates a new emission standard representation.
     * 
     * @param entityId the entity id
     * @param description the description
     * @param version the version
     * @param esLabel the es label
     */
    public EmissionStandardRepresentation(long entityId, String description, String version, String esLabel) {
        this.entityId = entityId;
        this.description = description;
        this.version = version;
        this.esLabel = esLabel;
    }

    /**
     * Gets the vehicle technology set.
     * 
     * @return the vehicle technology set
     */
    public List<VehicleTechnologyRepresentation> getVehicleTechnologySet() {
        return vehicleTechnologySet;
    }

    /**
     * Sets the vehicle technology set.
     * 
     * @param vehicleTechnologySet the new vehicle technology set
     */
    public void setVehicleTechnologySet(List<VehicleTechnologyRepresentation> vehicleTechnologySet) {
        this.vehicleTechnologySet = vehicleTechnologySet;
    }

    /**
     * Gets the fuel injection types.
     * 
     * @return the fuel injection types
     */
    public List<FuelInjectionTypeRepresentation> getFuelInjectionTypes() {
        return fuelInjectionTypes;
    }

    /**
     * Sets the fuel injection types.
     * 
     * @param fuelInjectionTypes the new fuel injection types
     */
    public void setFuelInjectionTypes(List<FuelInjectionTypeRepresentation> fuelInjectionTypes) {
        this.fuelInjectionTypes = fuelInjectionTypes;
    }

    /**
     * Gets the categories.
     * 
     * @return the categories
     */
    public List<CategoryRepresentation> getCategories() {
        return categories;
    }

    /**
     * Sets the categories.
     * 
     * @param categories the new categories
     */
    public void setCategories(List<CategoryRepresentation> categories) {
        this.categories = categories;
    }

    /**
     * Gets the fuels.
     * 
     * @return the fuels
     */
    public List<FuelRepresentation> getFuels() {
        return fuels;
    }

    /**
     * Sets the fuels.
     * 
     * @param fuels the new fuels
     */
    public void setFuels(List<FuelRepresentation> fuels) {
        this.fuels = fuels;
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
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the version.
     * 
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     * 
     * @param version the new version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the es label.
     * 
     * @return the es label
     */
    public String getEsLabel() {
        return esLabel;
    }

    /**
     * Sets the es label.
     * 
     * @param esLabel the new es label
     */
    public void setEsLabel(String esLabel) {
        this.esLabel = esLabel;
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    public StatusRepresentation getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * 
     * @param status the new status
     */
    public void setStatus(StatusRepresentation status) {
        this.status = status;
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

    /**
     * Gets the display label tvv.
     * 
     * @return the display label tvv
     */
    public String getDisplayLabelTVV() {
        return displayLabelTVV;
    }

    /**
     * Sets the display label tvv.
     * 
     * @param displayLabelTVV the new display label tvv
     */
    public void setDisplayLabelTVV(String displayLabelTVV) {
        this.displayLabelTVV = displayLabelTVV;
    }

}
