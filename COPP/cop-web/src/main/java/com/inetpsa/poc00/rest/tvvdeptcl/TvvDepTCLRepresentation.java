/*
 * Creation : Apr 1, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.List;

/**
 * The Class TvvDepTCLRepresentation.
 */
public class TvvDepTCLRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The description. */
	private String description;

	/** The version. */
	private String version;

	/** The label. */
	private String label;

	/** The generic test condition. */
	private List<GenericTestConditionRepresentation> genericTestCondition;

	/** The modified flag. */
	private String modifiedFlag;

	private int genericTestConditionLength;

	/**
	 * Instantiates a new tvv dep TCL representation.
	 */
	public TvvDepTCLRepresentation() {
		super();
	}

	/**
	 * Instantiates a new tvv dep TCL representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 */
	public TvvDepTCLRepresentation(long entityId, String description, String version, String label) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;

	}

	/**
	 * Instantiates a new tvv dep TCL representation.
	 * 
	 * @param entityId the entity id
	 */
	public TvvDepTCLRepresentation(Long entityId) {
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
	 * Gets the modified flag.
	 * 
	 * @return the modified flag
	 */
	public String getModifiedFlag() {
		return modifiedFlag;
	}

	/**
	 * Sets the modified flag.
	 * 
	 * @param modifiedFlag the new modified flag
	 */
	public void setModifiedFlag(String modifiedFlag) {
		this.modifiedFlag = modifiedFlag;
	}

	/**
	 * Gets the generic test condition.
	 * 
	 * @return the generic test condition
	 */
	public List<GenericTestConditionRepresentation> getGenericTestCondition() {
		return genericTestCondition;
	}

	/**
	 * Sets the generic test condition.
	 * 
	 * @param genericTestCondition the new generic test condition
	 */
	public void setGenericTestCondition(List<GenericTestConditionRepresentation> genericTestCondition) {
		this.genericTestCondition = genericTestCondition;
	}

	public int getGenericTestConditionLength() {
		return genericTestConditionLength;
	}

	public void setGenericTestConditionLength(int genericTestConditionLength) {
		this.genericTestConditionLength = genericTestConditionLength;
	}

}
