/*
 * Creation : Apr 1, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.emsdeptcl.EmsDepTCLRepresentation;
import com.inetpsa.poc00.rest.unit.UnitRepresentation;

/**
 * The Class GenericTestConditionRepresentation.
 */
public class GenericTestConditionRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The data type. */
	private String dataType;

	/** The label. */
	private String label;

	/** The default value. */
	private String defaultValue;

	/** The description. */
	private String description;

	/** The unit. */
	private UnitRepresentation unit;

	/** The is deleted. */
	private String isDeleted;

	/** The tvv dep TCL. */
	@JsonIgnore
	private TvvDepTCLRepresentation tvvDepTCL;

	/** The ems dep TCL. */
	@JsonIgnore
	private EmsDepTCLRepresentation emsDepTCL;

	/** The generic condition mandatory. */
	private List<GenericConditionMandatoryRepresentation> genericConditionMandatory;

	/**
	 * Instantiates a new generic test condition representation.
	 */
	public GenericTestConditionRepresentation() {
		super();
	}

	/**
	 * Instantiates a new generic test condition representation.
	 *
	 * @param entityId the entity id
	 * @param dataType the data type
	 * @param defaultValue the default value
	 * @param label the label
	 * @param unitId the unit id
	 * @param value the value
	 */
	public GenericTestConditionRepresentation(long entityId, String dataType, String defaultValue, String label, long unitId, String value) {
		this.entityId = entityId;
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.label = label;
		this.unit = new UnitRepresentation(unitId, value);
	}

	/**
	 * Instantiates a new generic test condition representation.
	 *
	 * @param entityId the entity id
	 * @param dataType the data type
	 * @param defaultValue the default value
	 * @param label the label
	 */
	public GenericTestConditionRepresentation(long entityId, String dataType, String defaultValue, String label) {
		this.entityId = entityId;
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.label = label;

	}

	/**
	 * Instantiates a new generic test condition representation.
	 *
	 * @param entityId the entity id
	 */
	public GenericTestConditionRepresentation(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param entityId the new entity id
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the data type.
	 *
	 * @return the data type
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Sets the data type.
	 *
	 * @param dataType the new data type
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the default value.
	 *
	 * @return the default value
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the default value.
	 *
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the tvv dep TCL.
	 *
	 * @return the tvv dep TCL
	 */
	public TvvDepTCLRepresentation getTvvDepTCL() {
		return tvvDepTCL;
	}

	/**
	 * Sets the tvv dep TCL.
	 *
	 * @param tvvDepTCL the new tvv dep TCL
	 */
	public void setTvvDepTCL(TvvDepTCLRepresentation tvvDepTCL) {
		this.tvvDepTCL = tvvDepTCL;
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
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public UnitRepresentation getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	public void setUnit(UnitRepresentation unit) {
		this.unit = unit;
	}

	/**
	 * Gets the checks if is deleted.
	 *
	 * @return the checks if is deleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}

	/**
	 * Sets the checks if is deleted.
	 *
	 * @param isDeleted the new checks if is deleted
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * Gets the ems dep TCL.
	 *
	 * @return the ems dep TCL
	 */
	public EmsDepTCLRepresentation getEmsDepTCL() {
		return emsDepTCL;
	}

	/**
	 * Sets the ems dep TCL.
	 *
	 * @param emsDepTCL the new ems dep TCL
	 */
	public void setEmsDepTCL(EmsDepTCLRepresentation emsDepTCL) {
		this.emsDepTCL = emsDepTCL;
	}

	/**
	 * Gets the generic condition mandatory.
	 *
	 * @return the generic condition mandatory
	 */
	public List<GenericConditionMandatoryRepresentation> getGenericConditionMandatory() {
		return genericConditionMandatory;
	}

	/**
	 * Sets the generic condition mandatory.
	 *
	 * @param genericConditionMandatory the new generic condition mandatory
	 */
	public void setGenericConditionMandatory(List<GenericConditionMandatoryRepresentation> genericConditionMandatory) {
		this.genericConditionMandatory = genericConditionMandatory;
	}

}
