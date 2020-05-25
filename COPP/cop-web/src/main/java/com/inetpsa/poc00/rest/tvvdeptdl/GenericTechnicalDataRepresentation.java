/*
 * Creation : Mar 23, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.emsdeptdl.EmsDepTDLRepresentation;
import com.inetpsa.poc00.rest.unit.UnitRepresentation;

/**
 * The Class GenericTechnicalDataRepresentation.
 */
public class GenericTechnicalDataRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The label. */
	private String label;

	/** The data type. */
	private String dataType;

	/** The default value. */
	private String defaultValue;

	/** The description. */
	private String description;

	/** The is deleted. */
	private String isDeleted;

	/** The is mandatory. */
	private String isMandatory;

	/** The tvv dep tdl. */
	@JsonIgnore
	private TvvDepTDLRepresentation tvvDepTDL;

	/** The ems dep tdl. */
	@JsonIgnore
	private EmsDepTDLRepresentation emsDepTDL;

	/** The Generic technical data manadatory. */
	private List<GenericTechDataMandatoryRepresentation> genericTechnicalDataManadatory;

	/** The unit. */
	private UnitRepresentation unit;

	/**
	 * Instantiates a new generic technical data representation.
	 */
	public GenericTechnicalDataRepresentation() {
		super();
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
	public GenericTechnicalDataRepresentation(long entityId, String dataType, String defaultValue, String label, long unitId, String unitValue) {
		this.entityId = entityId;
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.label = label;
		this.unit = new UnitRepresentation(unitId, unitValue);

	}

	/**
	 * Instantiates a new generic technical data representation.
	 * 
	 * @param entityId the entity id
	 */
	public GenericTechnicalDataRepresentation(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the ems dep tdl.
	 * 
	 * @return the ems dep tdl
	 */
	public EmsDepTDLRepresentation getEmsDepTDL() {
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
	public TvvDepTDLRepresentation getTvvDepTDL() {
		return tvvDepTDL;
	}

	/**
	 * Sets the tvv dep tdl.
	 * 
	 * @param tvvDepTDL the new tvv dep tdl
	 */
	public void setTvvDepTDL(TvvDepTDLRepresentation tvvDepTDL) {
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
	public void setEmsDepTDL(EmsDepTDLRepresentation emsDepTDL) {
		this.emsDepTDL = emsDepTDL;

	}

	/**
	 * Gets the generic technical data manadatory.
	 * 
	 * @return the generic technical data manadatory
	 */
	public List<GenericTechDataMandatoryRepresentation> getGenericTechnicalDataManadatory() {
		return genericTechnicalDataManadatory;
	}

	/**
	 * Sets the generic technical data manadatory.
	 * 
	 * @param dataList the new generic technical data manadatory
	 */
	public void setGenericTechnicalDataManadatory(List<GenericTechDataMandatoryRepresentation> dataList) {
		this.genericTechnicalDataManadatory = dataList;
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

}
