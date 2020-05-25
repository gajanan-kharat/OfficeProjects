/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtdl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLRepresentation;
import com.inetpsa.poc00.rest.unit.UnitRepresentation;

/**
 * The Class ValuedGenericDataRepresentation.
 */
public class ValuedGenericDataRepresentation extends BaseRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The label. */
	private String label;

	/** The data type. */
	private String dataType;

	/** The default value. */
	private String defaultValue;

	/** The unit. */
	/*
	private String unit;*/

	/** The description. */
	private String description;

	/** The is deleted. */
	private String isDeleted;

	/** The is mandatory. */
	private String isMandatory;

	/** The tvv dep tdl. */
	@JsonIgnore
	private TvvValuedTvvDepTDLRepresentation tvvDepTDL;

	/** The ems dep tdl. */
	@JsonIgnore
	private TvvValuedEsDepTDLRepresentation emsDepTDL;

	/** The unit. */
	private UnitRepresentation unit;

	private GenericTechnicalDataRepresentation genericData;

	/**
	 * Instantiates a new generic technical data representation.
	 */
	public ValuedGenericDataRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new generic technical data representation.
	 * 
	 * @param entityId the entity id
	 * @param dataType the data type
	 * @param defaultValue the default value
	 * @param label the label
	 * @param unitId the unit id
	 * @param unitValue the unit value
	 */
	public ValuedGenericDataRepresentation(long entityId, String dataType, String defaultValue, String label, long unitId, String unitValue) {
		this.entityId = entityId;
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.label = label;
		this.unit = new UnitRepresentation(unitId, unitValue);

	}

	/**
	 * Gets the ems dep tdl.
	 * 
	 * @return the ems dep tdl
	 */
	public TvvValuedEsDepTDLRepresentation getEmsDepTDL() {
		return emsDepTDL;
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
	 * @return the label
	 */

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
	 * Gets the tvv dep tdl.
	 * 
	 * @return the tvv dep tdl
	 */
	public TvvValuedTvvDepTDLRepresentation getTvvDepTDL() {
		return tvvDepTDL;
	}

	/**
	 * Sets the tvv dep tdl.
	 * 
	 * @param tvvDepTDL the new tvv dep tdl
	 */
	public void setTvvDepTDL(TvvValuedTvvDepTDLRepresentation tvvDepTDL) {
		this.tvvDepTDL = tvvDepTDL;
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
	 * Sets the ems dep tdl.
	 * 
	 * @param emsDepTDL the new ems dep tdl
	 */
	public void setEmsDepTDL(TvvValuedEsDepTDLRepresentation emsDepTDL) {
		this.emsDepTDL = emsDepTDL;

	}

	/**
	 * Gets the checks if is mandatory.
	 * 
	 * @return the checks if is mandatory
	 */
	public String getIsMandatory() {
		return isMandatory;
	}

	/**
	 * Sets the checks if is mandatory.
	 * 
	 * @param isMandatory the new checks if is mandatory
	 */
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
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
	 * Sets the unit.
	 * 
	 * @param unit the new unit
	 */
	public void setUnit(UnitRepresentation unit) {
		this.unit = unit;
	}

	/**
	 * Gets the unit.
	 * 
	 * @return the unit
	 */
	public UnitRepresentation getUnit() {

		return this.unit;
	}

	public GenericTechnicalDataRepresentation getGenericData() {
		return genericData;
	}

	public void setGenericData(GenericTechnicalDataRepresentation genericData) {
		this.genericData = genericData;
	}

}
