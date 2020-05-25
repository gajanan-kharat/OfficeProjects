/*
 * Creation : Mar 21, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.List;

/**
 * The Class TvvDepTDLRepresentation.
 */
public class TvvDepTDLRepresentation {
	
	/** The entity id. */
	private long entityId;
	
	/** The description. */
	private String description;

	/** The version. */
	private String version;

	/** The label. */
	private String label;
	
	/** The generic technical data. */
	private List<GenericTechnicalDataRepresentation> genericTechnicalData;
	
	/** The modified flag. */
	private String modifiedFlag;

	/**
	 * Instantiates a new tvv dep tdl representation.
	 */
	public TvvDepTDLRepresentation() {
		super();
	}

	/**
	 * Instantiates a new tvv dep tdl representation.
	 *
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 */
	public TvvDepTDLRepresentation(long entityId, String description, String version, String label) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;

	}

	/**
	 * Instantiates a new tvv dep tdl representation.
	 *
	 * @param entityId the entity id
	 */
	public TvvDepTDLRepresentation(Long entityId) {
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
	 * Gets the generic technical data.
	 *
	 * @return the generic technical data
	 */
	public List<GenericTechnicalDataRepresentation> getGenericTechnicalData() {
		return genericTechnicalData;
	}

	/**
	 * Sets the generic technical data.
	 *
	 * @param genericTechnicalData the new generic technical data
	 */
	public void setGenericTechnicalData(List<GenericTechnicalDataRepresentation> genericTechnicalData) {
		this.genericTechnicalData = genericTechnicalData;
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

}
