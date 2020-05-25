/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.rest.vehicletechnology;

/**
 * The Class VehicleTechnologyRepresentation.
 */
public class VehicleTechnologyRepresentation {

	/** The entity id. */
	protected Long entityId;

	/** The description. */
	private String description;

	/** The label. */
	private String label;

	/** The edited. */
	private boolean edited = false;

	/**
	 * Instantiates a new vehicle technology representation.
	 */
	public VehicleTechnologyRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new vehicle technology representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param label the label
	 */
	public VehicleTechnologyRepresentation(Long entityId, String description, String label) {
		this.entityId = entityId;
		this.description = description;
		this.label = label;

	}

	/**
	 * Instantiates a new vehicle technology representation.
	 * 
	 * @param entityId the entity id
	 */
	public VehicleTechnologyRepresentation(Long entityId) {
		this.entityId = entityId;

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
	 * Checks if is edited.
	 * 
	 * @return true, if is edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Sets the edited.
	 * 
	 * @param edited the new edited
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

}
