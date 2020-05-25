/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtcl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;

/**
 * The Class TvvValuedTvvDepTCLRepresentation.
 */
public class TvvValuedTvvDepTCLRepresentation extends BaseRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The description. */
	private String description;

	/** The version. */
	private String version;

	/** The label. */
	private String label;

	/** The generic test condition. */
	private List<ValuedGenericConditionRepresentation> genericTestCondition;

	/** The modified flag. */
	private String modifiedFlag;

	/** The edited values. */
	private int editedValues;

	/** The tvv obj. */
	@JsonIgnore
	private TvvRepresentation tvvObj;

	private int genericTestConditionLength;

	/**
	 * Instantiates a new tvv valued tvv dep tcl representation.
	 */
	public TvvValuedTvvDepTCLRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new tvv valued tvv dep tcl representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 */
	public TvvValuedTvvDepTCLRepresentation(long entityId, String description, String version, String label) {
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.common.BaseRepresentation#getModifiedFlag()
	 */
	@Override
	public String getModifiedFlag() {
		return modifiedFlag;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.common.BaseRepresentation#setModifiedFlag(java.lang.String)
	 */
	@Override
	public void setModifiedFlag(String modifiedFlag) {
		this.modifiedFlag = modifiedFlag;
	}

	/**
	 * Gets the generic test condition.
	 * 
	 * @return the generic test condition
	 */
	public List<ValuedGenericConditionRepresentation> getGenericTestCondition() {
		return genericTestCondition;
	}

	/**
	 * Sets the generic test condition.
	 * 
	 * @param genericTestCondition the new generic test condition
	 */
	public void setGenericTestCondition(List<ValuedGenericConditionRepresentation> genericTestCondition) {
		this.genericTestCondition = genericTestCondition;
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

	public int getGenericTestConditionLength() {
		return genericTestConditionLength;
	}

	public void setGenericTestConditionLength(int genericTestConditionLength) {
		this.genericTestConditionLength = genericTestConditionLength;
	}

}