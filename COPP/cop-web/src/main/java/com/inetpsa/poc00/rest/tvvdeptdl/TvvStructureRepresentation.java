package com.inetpsa.poc00.rest.tvvdeptdl;

/**
 * The Class TvvStructureRepresentation.
 */
public class TvvStructureRepresentation {

	/** The entity id. */
	private long entityId;

	/** The description. */
	private String description;

	/** The version. */
	private String version;

	/** The label. */
	private String label;

	/** The list type. */
	private String listType;
	
	private String mandatDisplayTVVLabel;

	/**
	 * Instantiates a new tvv structure representation.
	 *
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 */
	public TvvStructureRepresentation(long entityId, String description, String version, String label) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;
		this.setMandatDisplayTVVLabel(label + " " + version);
	}

	/**
	 * Instantiates a new tvv structure representation.
	 */
	public TvvStructureRepresentation() {
		super();
	}

	/**
	 * Instantiates a new tvv structure representation.
	 *
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 * @param listType the list type
	 */
	public TvvStructureRepresentation(long entityId, String description, String version, String label, String listType) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;
		this.listType = listType;
		this.setMandatDisplayTVVLabel(label + " " + version);
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the entityId
	 */
	public long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param entityId the entityId to set
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
	 * @param description the description to set
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
	 * @param version the version to set
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
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the list type.
	 *
	 * @return the listType
	 */
	public String getListType() {
		return listType;
	}

	/**
	 * Sets the list type.
	 *
	 * @param listType the listType to set
	 */
	public void setListType(String listType) {
		this.listType = listType;
	}

	/**
	 * @return the mandatDisplayTVVLabel
	 */
	public String getMandatDisplayTVVLabel() {
		return mandatDisplayTVVLabel;
	}

	/**
	 * @param mandatDisplayTVVLabel the mandatDisplayTVVLabel to set
	 */
	public void setMandatDisplayTVVLabel(String mandatDisplayTVVLabel) {
		this.mandatDisplayTVVLabel = mandatDisplayTVVLabel;
	}

}
