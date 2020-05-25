package com.inetpsa.poc00.rest.engine;

import java.math.BigInteger;

/**
 * The Class EngineRepresentation.
 */
public class EngineRepresentation {

    /** The entity id. */
    private Long entityId;

    /** The bof value. */
    private String bofValue;

    /** The doc value. */
    private String docValue;

    /** The kmat. */
    private String kmat;

    /** The fr label b0 f. */
    private String frLabelB0F;

    /** The fr label doc. */
    private String frLabelDOC;

    /** The engine label. */
    private String engineLabel;

    /** The power kw. */
    private String powerKw;

    /** The power cv. */
    private String powerCv;

    /** The torque. */
    private String torque;

    /** The label derogation. */
    private String labelDerogation;

    /** The Fuel injection_ id. */
    private Long fuelInjectionID;

    /** The Fuel type_ id. */
    private Long fuelTypeID;

    /** The tvv rule id b0 f. */
    private Long tvvRuleIdB0F;

    /** The tvv rule id doc. */
    private Long tvvRuleIdDOC;

    /** The engine entity id. */
    private Long engineEntityID;

    /** The fuel injection_label. */
    private String fuelInjectionlabel;

    /** The fuel type_label. */
    private String fuelTypelabel;

    /** The display label. */
    private String displayLabel;

    /**
     * Instantiates a new engine representation.
     * 
     * @param engineLabel the engine label
     * @param engineEntityID the engine entity id
     */
    public EngineRepresentation(String engineLabel, Long engineEntityID, String B0FValue, String D0CValue) {

        this.engineLabel = B0FValue + "-" + D0CValue + "-" + engineLabel;
        this.engineEntityID = engineEntityID;
    }

    /**
     * Instantiates a new engine representation.
     * 
     * @param bofValue the bof value
     * @param docValue the doc value
     * @param kmat the kmat
     * @param frLabelB0F the fr label b0 f
     * @param frLabelDOC the fr label doc
     * @param engineLabel the engine label
     * @param powerKw the power kw
     * @param powerCv the power cv
     * @param torque the torque
     * @param labelDerogation the label derogation
     * @param engineEntityID the engine entity id
     * @param TvvRuleIdB0F the tvv rule id b0 f
     * @param TvvRuleIdDOC the tvv rule id doc
     * @param FuelType_ID the fuel type_ id
     * @param FuelInjection_ID the fuel injection_ id
     */
    public EngineRepresentation(String bofValue, String docValue, String kmat, String frLabelB0F, String frLabelDOC, String engineLabel,
            String powerKw, String powerCv, String torque, String labelDerogation, BigInteger engineEntityID, BigInteger TvvRuleIdB0F,
            BigInteger TvvRuleIdDOC, Integer FuelType_ID, BigInteger FuelInjection_ID) {

        this.bofValue = bofValue;
        this.docValue = docValue;
        this.kmat = kmat;
        this.frLabelB0F = frLabelB0F;
        this.frLabelDOC = frLabelDOC;
        this.engineLabel = engineLabel;
        if (engineLabel == null) {
            this.engineLabel = frLabelB0F;
        }
        this.powerKw = powerKw;
        this.powerCv = powerCv;
        this.torque = torque;
        this.labelDerogation = labelDerogation;

        if (engineEntityID != null)
            this.engineEntityID = engineEntityID.longValue();

        if (TvvRuleIdB0F != null)
            this.tvvRuleIdB0F = TvvRuleIdB0F.longValue();

        if (TvvRuleIdB0F != null)
            this.tvvRuleIdDOC = TvvRuleIdDOC.longValue();

        if (FuelInjection_ID != null)
            this.fuelInjectionID = FuelInjection_ID.longValue();

        if (FuelType_ID != null)
            this.fuelTypeID = FuelType_ID.longValue();

        this.displayLabel = this.engineLabel + "-" + this.powerCv + "-" + this.torque;
    }

    /**
     * Instantiates a new engine representation.
     * 
     * @param bofValue the bof value
     * @param docValue the doc value
     * @param kmat the kmat
     * @param frLabelB0F the fr label b0 f
     * @param frLabelDOC the fr label doc
     * @param engineLabel the engine label
     * @param powerKw the power kw
     * @param powerCv the power cv
     * @param torque the torque
     * @param labelDerogation the label derogation
     * @param engineEntityID the engine entity id
     * @param TvvRuleIdB0F the tvv rule id b0 f
     * @param TvvRuleIdDOC the tvv rule id doc
     * @param FuelType_ID the fuel type_ id
     * @param FuelInjection_ID the fuel injection_ id
     */
    public EngineRepresentation(String bofValue, String docValue, String kmat, String frLabelB0F, String frLabelDOC, String engineLabel,
            String powerKw, String powerCv, String torque, String labelDerogation, Long engineEntityID, Long TvvRuleIdB0F, Long TvvRuleIdDOC,
            Long FuelType_ID, Long FuelInjection_ID) {

        this.bofValue = bofValue;
        this.kmat = kmat;
        this.frLabelB0F = frLabelB0F;
        this.frLabelDOC = frLabelDOC;
        this.docValue = docValue;
        this.engineLabel = engineLabel;
        if (engineLabel == null) {
            this.engineLabel = frLabelB0F;
        }
        this.powerKw = powerKw;
        this.powerCv = powerCv;
        this.torque = torque;
        this.labelDerogation = labelDerogation;
        this.engineEntityID = engineEntityID;
        this.tvvRuleIdB0F = TvvRuleIdB0F;
        this.tvvRuleIdDOC = TvvRuleIdDOC;
        this.fuelInjectionID = FuelInjection_ID;
        this.fuelTypeID = FuelType_ID;
        this.displayLabel = this.engineLabel + "-" + this.powerCv + "-" + this.torque;
    }

    /**
     * Instantiates a new engine representation.
     *
     * @param entityId the entity id
     * @param engineLabel the engine label
     * @param powerKw the power kw
     * @param powerCv the power cv
     * @param torque the torque
     * @param b0FValue the b0 f value
     * @param d0CValue the d0 c value
     */
    public EngineRepresentation(Long entityId, String engineLabel, String powerKw, String powerCv, String torque, String b0FValue, String d0CValue) {
        this.setEngineEntityID(entityId);
        this.engineLabel = engineLabel;
        this.powerKw = powerKw;
        this.powerCv = powerCv;
        this.torque = torque;

        this.displayLabel = b0FValue + "-" + d0CValue + "-" + this.engineLabel + "-" + this.powerCv + "-" + this.torque;
    }

    /**
     * Instantiates a new engine representation.
     * 
     * @param entityId the entity id
     * @param engineLabel the engine label
     * @param powerKw the power kw
     * @param powerCv the power cv
     * @param torque the torque
     */
    public EngineRepresentation(Long entityId, String engineLabel, String powerKw, String powerCv, String torque) {
        this.setEngineEntityID(entityId);
        this.engineLabel = engineLabel;
        this.powerKw = powerKw;
        this.powerCv = powerCv;
        this.torque = torque;

        this.displayLabel = this.engineLabel + "-" + this.powerCv + "-" + this.torque;
    }

    /**
     * Instantiates a new engine representation.
     *
     * @param engineLabel the engine label
     */
    public EngineRepresentation(String engineLabel) {
        this.engineLabel = engineLabel;
    }

    /**
     * Instantiates a new engine representation.
     */
    public EngineRepresentation() {
        super();
    }

    /**
     * Gets the bof value.
     * 
     * @return the bof value
     */
    public String getBofValue() {
        return bofValue;
    }

    /**
     * Sets the bof value.
     * 
     * @param bofValue the new bof value
     */
    public void setBofValue(String bofValue) {
        this.bofValue = bofValue;
    }

    /**
     * Gets the doc value.
     * 
     * @return the doc value
     */
    public String getDocValue() {
        return docValue;
    }

    /**
     * Sets the doc value.
     * 
     * @param docValue the new doc value
     */
    public void setDocValue(String docValue) {
        this.docValue = docValue;
    }

    /**
     * Gets the kmat.
     * 
     * @return the kmat
     */
    public String getKmat() {
        return kmat;
    }

    /**
     * Sets the kmat.
     * 
     * @param kmat the new kmat
     */
    public void setKmat(String kmat) {
        this.kmat = kmat;
    }

    /**
     * Gets the engine label.
     * 
     * @return the engine label
     */
    public String getEngineLabel() {
        return engineLabel;
    }

    /**
     * Sets the engine label.
     * 
     * @param engineLabel the new engine label
     */
    public void setEngineLabel(String engineLabel) {
        this.engineLabel = engineLabel;
    }

    /**
     * Gets the power kw.
     * 
     * @return the power kw
     */
    public String getPowerKw() {
        return powerKw;
    }

    /**
     * Sets the power kw.
     * 
     * @param powerKw the new power kw
     */
    public void setPowerKw(String powerKw) {
        this.powerKw = powerKw;
    }

    /**
     * Gets the power cv.
     * 
     * @return the power cv
     */
    public String getPowerCv() {
        return powerCv;
    }

    /**
     * Sets the power cv.
     * 
     * @param powerCv the new power cv
     */
    public void setPowerCv(String powerCv) {
        this.powerCv = powerCv;
    }

    /**
     * Gets the torque.
     * 
     * @return the torque
     */
    public String getTorque() {
        return torque;
    }

    /**
     * Sets the torque.
     * 
     * @param torque the new torque
     */
    public void setTorque(String torque) {
        this.torque = torque;
    }

    /**
     * Gets the label derogation.
     * 
     * @return the label derogation
     */
    public String getLabelDerogation() {
        return labelDerogation;
    }

    /**
     * Sets the label derogation.
     * 
     * @param labelDerogation the new label derogation
     */
    public void setLabelDerogation(String labelDerogation) {
        this.labelDerogation = labelDerogation;
    }

    /**
     * Gets the fuel injection id.
     * 
     * @return the fuel injection id
     */
    public Long getFuelInjectionID() {
        return fuelInjectionID;
    }

    /**
     * Sets the fuel injection id.
     * 
     * @param fuelInjectionID the new fuel injection id
     */
    public void setFuelInjectionID(Long fuelInjectionID) {
        this.fuelInjectionID = fuelInjectionID;
    }

    /**
     * Gets the fuel type id.
     * 
     * @return the fuel type id
     */
    public Long getFuelTypeID() {
        return fuelTypeID;
    }

    /**
     * Sets the fuel type id.
     * 
     * @param fuelTypeID the new fuel type id
     */
    public void setFuelTypeID(Long fuelTypeID) {
        this.fuelTypeID = fuelTypeID;
    }

    /**
     * Gets the fuel injectionlabel.
     * 
     * @return the fuel injectionlabel
     */
    public String getFuelInjectionlabel() {
        return fuelInjectionlabel;
    }

    /**
     * Sets the fuel injectionlabel.
     * 
     * @param fuelInjectionlabel the new fuel injectionlabel
     */
    public void setFuelInjectionlabel(String fuelInjectionlabel) {
        this.fuelInjectionlabel = fuelInjectionlabel;
    }

    /**
     * Gets the fuel typelabel.
     * 
     * @return the fuel typelabel
     */
    public String getFuelTypelabel() {
        return fuelTypelabel;
    }

    /**
     * Sets the fuel typelabel.
     * 
     * @param fuelTypelabel the new fuel typelabel
     */
    public void setFuelTypelabel(String fuelTypelabel) {
        this.fuelTypelabel = fuelTypelabel;
    }

    /**
     * Gets the fr label b0 f.
     * 
     * @return the fr label b0 f
     */
    public String getFrLabelB0F() {
        return frLabelB0F;
    }

    /**
     * Sets the fr label b0 f.
     * 
     * @param frLabelB0F the new fr label b0 f
     */
    public void setFrLabelB0F(String frLabelB0F) {
        this.frLabelB0F = frLabelB0F;
    }

    /**
     * Gets the fr label doc.
     * 
     * @return the fr label doc
     */
    public String getFrLabelDOC() {
        return frLabelDOC;
    }

    /**
     * Sets the fr label doc.
     * 
     * @param frLabelDOC the new fr label doc
     */
    public void setFrLabelDOC(String frLabelDOC) {
        this.frLabelDOC = frLabelDOC;
    }

    /**
     * Gets the tvv rule id b0 f.
     * 
     * @return the tvv rule id b0 f
     */
    public Long getTvvRuleIdB0F() {
        return tvvRuleIdB0F;
    }

    /**
     * Sets the tvv rule id b0 f.
     * 
     * @param tvvRuleIdB0F the new tvv rule id b0 f
     */
    public void setTvvRuleIdB0F(Long tvvRuleIdB0F) {
        this.tvvRuleIdB0F = tvvRuleIdB0F;
    }

    /**
     * Gets the tvv rule id doc.
     * 
     * @return the tvv rule id doc
     */
    public Long getTvvRuleIdDOC() {
        return tvvRuleIdDOC;
    }

    /**
     * Sets the tvv rule id doc.
     * 
     * @param tvvRuleIdDOC the new tvv rule id doc
     */
    public void setTvvRuleIdDOC(Long tvvRuleIdDOC) {
        this.tvvRuleIdDOC = tvvRuleIdDOC;
    }

    /**
     * Gets the engine entity id.
     * 
     * @return the engine entity id
     */
    public Long getEngineEntityID() {
        return engineEntityID;
    }

    /**
     * Sets the engine entity id.
     * 
     * @param engineEntityID the new engine entity id
     */
    public void setEngineEntityID(Long engineEntityID) {
        this.engineEntityID = engineEntityID;
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
