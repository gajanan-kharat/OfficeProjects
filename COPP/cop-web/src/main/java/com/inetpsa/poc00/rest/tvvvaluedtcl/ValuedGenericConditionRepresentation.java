/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtcl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLRepresentation;
import com.inetpsa.poc00.rest.unit.UnitRepresentation;

/**
 * The Class ValuedGenericConditionRepresentation.
 */
public class ValuedGenericConditionRepresentation extends BaseRepresentation {
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

	/** The tvv dep tcl. */
	@JsonIgnore
	private TvvValuedTvvDepTCLRepresentation tvvDepTCL;

	/** The ems dep tcl. */
	@JsonIgnore
	private TvvValuedEsDepTCLRepresentation emsDepTCL;

	/** The generic condition. */
	private GenericTestConditionRepresentation genericCondition;

	/**
	 * Instantiates a new generic test condition representation.
	 */
	public ValuedGenericConditionRepresentation() {
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
	public ValuedGenericConditionRepresentation(long entityId, String dataType, String defaultValue, String label, long unitId, String value) {
		this.entityId = entityId;
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.label = label;
		this.unit = new UnitRepresentation(unitId, value);
	}

	/**
	 * Instantiates a new valued generic condition representation.
	 * 
	 * @param entityId the entity id
	 * @param dataType the data type
	 * @param defaultValue the default value
	 * @param label the label
	 */
	public ValuedGenericConditionRepresentation(long entityId, String dataType, String defaultValue, String label) {
		this.entityId = entityId;
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.label = label;

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
	 * Gets the tvv dep tcl.
	 * 
	 * @return the tvv dep tcl
	 */
	public TvvValuedTvvDepTCLRepresentation getTvvDepTCL() {
		return tvvDepTCL;
	}

	/**
	 * Sets the tvv dep tcl.
	 * 
	 * @param tvvDepTCL the new tvv dep tcl
	 */
	public void setTvvDepTCL(TvvValuedTvvDepTCLRepresentation tvvDepTCL) {
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
	 * Gets the ems dep tcl.
	 * 
	 * @return the ems dep tcl
	 */
	public TvvValuedEsDepTCLRepresentation getEmsDepTCL() {
		return emsDepTCL;
	}

	/**
	 * Sets the ems dep tcl.
	 * 
	 * @param emsDepTCL the new ems dep tcl
	 */
	public void setEmsDepTCL(TvvValuedEsDepTCLRepresentation emsDepTCL) {
		this.emsDepTCL = emsDepTCL;
	}

	/**
	 * Gets the generic condition.
	 * 
	 * @return the generic condition
	 */
	public GenericTestConditionRepresentation getGenericCondition() {
		return genericCondition;
	}

	/**
	 * Sets the generic condition.
	 * 
	 * @param genericCondition the new generic condition
	 */
	public void setGenericCondition(GenericTestConditionRepresentation genericCondition) {
		this.genericCondition = genericCondition;
	}

}
