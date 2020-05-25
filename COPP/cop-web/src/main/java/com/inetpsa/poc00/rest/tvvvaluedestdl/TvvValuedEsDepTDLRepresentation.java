/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedestdl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataRepresentation;

/**
 * The Class TvvValuedEsDepTDLRepresentation.
 */
public class TvvValuedEsDepTDLRepresentation extends BaseRepresentation {

	/** The entity id. */
	private long entityId;

	/** The description. */
	private String description;

	/** The version. */
	private String version;

	/** The label. */
	private String label;

	/** The valued generic data. */
	private List<ValuedGenericDataRepresentation> valuedGenericData;

	/** The modified flag. */
	private String modifiedFlag;

	/** The edited values. */
	private int editedValues;
	@JsonIgnore
	private TvvRepresentation tvvObj;

	private int valuedGenericDataLength;

	/**
	 * Instantiates a new tvv valued es dep tdl representation.
	 */
	public TvvValuedEsDepTDLRepresentation() {
		super();
	}

	/**
	 * Instantiates a new tvv valued es dep tdl representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 */
	public TvvValuedEsDepTDLRepresentation(long entityId, String description, String version, String label) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
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

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.common.BaseRepresentation#getModifiedFlag()
	 */
	@Override
	public String getModifiedFlag() {
		return modifiedFlag;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.common.BaseRepresentation#setModifiedFlag(java.lang.String)
	 */
	@Override
	public void setModifiedFlag(String modifiedFlag) {
		this.modifiedFlag = modifiedFlag;
	}

	/**
	 * Gets the valued generic data.
	 * 
	 * @return the valued generic data
	 */
	public List<ValuedGenericDataRepresentation> getValuedGenericData() {
		return valuedGenericData;
	}

	/**
	 * Sets the valued generic data.
	 * 
	 * @param valuedGenericData the new valued generic data
	 */
	public void setValuedGenericData(List<ValuedGenericDataRepresentation> valuedGenericData) {
		this.valuedGenericData = valuedGenericData;
	}

	/**
	 * Gets the edited values.
	 * 
	 * @return the edited values
	 */
	public int getEditedValues() {
		return editedValues;
	}

	/**
	 * Sets the edited values.
	 * 
	 * @param editedValues the new edited values
	 */
	public void setEditedValues(int editedValues) {
		this.editedValues = editedValues;
	}

	public TvvRepresentation getTvvObj() {
		return tvvObj;
	}

	public void setTvvObj(TvvRepresentation tvvObj) {
		this.tvvObj = tvvObj;
	}

	public int getValuedGenericDataLength() {
		return valuedGenericDataLength;
	}

	public void setValuedGenericDataLength(int valuedGenericDataLength) {
		this.valuedGenericDataLength = valuedGenericDataLength;
	}

}
