package com.inetpsa.poc00.rest.gearbox;

import java.math.BigInteger;

/**
 * The Class GearBoxRepresentation.
 */
public class GearBoxRepresentation {

    /** The dbm value. */
    private String dbmValue;

    /** The b0g value. */
    private String b0gValue;

    /** The kmat. */
    private String kmat;

    /** The dbm fr label. */
    private String dbmFRLabel;

    /** The bog fr label. */
    private String bogFRLabel;

    /** The gear box label. */
    private String gearBoxLabel;

    /** The gear box type. */
    private String gearBoxType;

    /** The entity id. */
    private Long entityId;

    /** The display label. */
    private String displayLabel;

    /** The gearbox entity. */
    private Long gearboxEntity;

    /** The dbm tvv rule id. */
    private Long dbmTVVRuleId;

    /** The b0g tvv rule entity. */
    private Long b0gTVVRuleEntity;

    /**
     * Instantiates a new gear box representation.
     */
    public GearBoxRepresentation() {
        super();
    }

    /**
     * Instantiates a new gear box representation.
     *
     * @param dbmValue the dbm value
     * @param b0gValue the b0g value
     * @param kamt the kamt
     * @param dbmFRLabel the dbm fr label
     * @param bogFRLabel the bog fr label
     * @param gearBoxLabel the gear box label
     * @param gearBoxType the gear box type
     * @param gearboxEntity the gearbox entity
     * @param dbmTVVRuleId the dbm tvv rule id
     * @param b0gTVVRuleEntity the b0g tvv rule entity
     */
    public GearBoxRepresentation(String dbmValue, String b0gValue, String kamt, String dbmFRLabel, String bogFRLabel, String gearBoxLabel,
            String gearBoxType, BigInteger gearboxEntity, BigInteger dbmTVVRuleId, BigInteger b0gTVVRuleEntity) {

        this.dbmValue = dbmValue;
        this.b0gValue = b0gValue;
        this.kmat = kamt;
        this.dbmFRLabel = dbmFRLabel;
        this.bogFRLabel = bogFRLabel;
        if (gearBoxLabel == null) {
            gearBoxLabel = dbmFRLabel;
        }
        this.gearBoxLabel = gearBoxLabel;
        this.gearBoxType = gearBoxType;

        if (gearboxEntity != null)
            this.gearboxEntity = gearboxEntity.longValue();

        if (dbmTVVRuleId != null)
            this.dbmTVVRuleId = dbmTVVRuleId.longValue();

        if (b0gTVVRuleEntity != null)
            this.b0gTVVRuleEntity = b0gTVVRuleEntity.longValue();

    }

    /**
     * Instantiates a new gear box representation.
     *
     * @param dbmValue the dbm value
     * @param b0gValue the b0g value
     * @param kamt the kamt
     * @param dbmFRLabel the dbm fr label
     * @param bogFRLabel the bog fr label
     * @param gearBoxLabel the gear box label
     * @param gearBoxType the gear box type
     * @param gearboxEntity the gearbox entity
     * @param dbmTVVRuleId the dbm tvv rule id
     * @param b0gTVVRuleEntity the b0g tvv rule entity
     */
    public GearBoxRepresentation(String dbmValue, String b0gValue, String kamt, String dbmFRLabel, String bogFRLabel, String gearBoxLabel,
            String gearBoxType, Long gearboxEntity, Long dbmTVVRuleId, Long b0gTVVRuleEntity) {

        this.dbmValue = dbmValue;
        this.b0gValue = b0gValue;
        this.kmat = kamt;
        this.dbmFRLabel = dbmFRLabel;
        this.bogFRLabel = bogFRLabel;
        if (gearBoxLabel == null) {
            gearBoxLabel = dbmFRLabel;
        }
        this.gearBoxLabel = gearBoxLabel;
        this.gearBoxType = gearBoxType;
        this.gearboxEntity = gearboxEntity;
        this.dbmTVVRuleId = dbmTVVRuleId;
        this.b0gTVVRuleEntity = b0gTVVRuleEntity;

    }

    /**
     * Instantiates a new gear box representation.
     *
     * @param gearBoxLabel the gear box label
     * @param gearboxEntity the gearbox entity
     */
    public GearBoxRepresentation(String gearBoxLabel, Long gearboxEntity) {
        this.gearBoxLabel = gearBoxLabel;
        this.gearboxEntity = gearboxEntity;
    }

    /**
     * Instantiates a new gear box representation.
     *
     * @param entityId the entity id
     * @param gearBoxLabel the gear box label
     * @param gearBoxType the gear box type
     * @param DBMValue the DBM value
     * @param B0GValue the b0 g value
     */
    public GearBoxRepresentation(Long entityId, String gearBoxLabel, String gearBoxType, String dBMValue, String b0GValue) {

        this.gearBoxLabel = gearBoxLabel;
        this.gearBoxType = gearBoxType;
        this.gearboxEntity = entityId;
        this.setDisplayLabel(dBMValue + "-" + b0GValue + "-" + this.gearBoxLabel + " " + this.gearBoxType);

    }

    /**
     * Instantiates a new gear box representation.
     * 
     * @param entityId the entity id
     * @param gearBoxLabel the gear box label
     * @param gearBoxType the gear box type
     */
    public GearBoxRepresentation(Long entityId, String gearBoxLabel, String gearBoxType) {

        this.gearBoxLabel = gearBoxLabel;
        this.gearBoxType = gearBoxType;
        this.gearboxEntity = entityId;
        this.setDisplayLabel(this.gearBoxLabel + " " + this.gearBoxType);

    }

    /**
     * Gets the dbm value.
     *
     * @return the dbm value
     */
    public String getDbmValue() {
        return dbmValue;
    }

    /**
     * Sets the dbm value.
     *
     * @param dbmValue the new dbm value
     */
    public void setDbmValue(String dbmValue) {
        this.dbmValue = dbmValue;
    }

    /**
     * Gets the b0g value.
     *
     * @return the b0g value
     */
    public String getB0gValue() {
        return b0gValue;
    }

    /**
     * Sets the b0g value.
     *
     * @param b0gValue the new b0g value
     */
    public void setB0gValue(String b0gValue) {
        this.b0gValue = b0gValue;
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
     * Gets the dbm fr label.
     *
     * @return the dbm fr label
     */
    public String getDbmFRLabel() {
        return dbmFRLabel;
    }

    /**
     * Sets the dbm fr label.
     *
     * @param dbmFRLabel the new dbm fr label
     */
    public void setDbmFRLabel(String dbmFRLabel) {
        this.dbmFRLabel = dbmFRLabel;
    }

    /**
     * Gets the bog fr label.
     *
     * @return the bog fr label
     */
    public String getBogFRLabel() {
        return bogFRLabel;
    }

    /**
     * Sets the bog fr label.
     *
     * @param bogFRLabel the new bog fr label
     */
    public void setBogFRLabel(String bogFRLabel) {
        this.bogFRLabel = bogFRLabel;
    }

    /**
     * Gets the gear box label.
     *
     * @return the gear box label
     */
    public String getGearBoxLabel() {
        return gearBoxLabel;
    }

    /**
     * Sets the gear box label.
     *
     * @param gearBoxLabel the new gear box label
     */
    public void setGearBoxLabel(String gearBoxLabel) {
        this.gearBoxLabel = gearBoxLabel;
    }

    /**
     * Gets the gear box type.
     *
     * @return the gear box type
     */
    public String getGearBoxType() {
        return gearBoxType;
    }

    /**
     * Sets the gear box type.
     *
     * @param gearBoxType the new gear box type
     */
    public void setGearBoxType(String gearBoxType) {
        this.gearBoxType = gearBoxType;
    }

    /**
     * Gets the gearbox entity.
     *
     * @return the gearbox entity
     */
    public Long getGearboxEntity() {
        return gearboxEntity;
    }

    /**
     * Sets the gearbox entity.
     *
     * @param gearboxEntity the new gearbox entity
     */
    public void setGearboxEntity(Long gearboxEntity) {
        this.gearboxEntity = gearboxEntity;
    }

    /**
     * Gets the dbm tvv rule id.
     *
     * @return the dbm tvv rule id
     */
    public Long getDbmTVVRuleId() {
        return dbmTVVRuleId;
    }

    /**
     * Sets the dbm tvv rule id.
     *
     * @param dbmTVVRuleId the new dbm tvv rule id
     */
    public void setDbmTVVRuleId(Long dbmTVVRuleId) {
        this.dbmTVVRuleId = dbmTVVRuleId;
    }

    /**
     * Gets the b0g tvv rule entity.
     *
     * @return the b0g tvv rule entity
     */
    public Long getB0gTVVRuleEntity() {
        return b0gTVVRuleEntity;
    }

    /**
     * Sets the b0g tvv rule entity.
     *
     * @param b0gTVVRuleEntity the new b0g tvv rule entity
     */
    public void setB0gTVVRuleEntity(Long b0gTVVRuleEntity) {
        this.b0gTVVRuleEntity = b0gTVVRuleEntity;
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
