package com.inetpsa.poc00.rest.fuelinjectiontype;

/**
 * The Class FuelInjectionTypeRepresentation.
 */
public class FuelInjectionTypeRepresentation {

	/** The entity id. */
	protected Long entityId;

	/** The label. */
	private String label;

	/** The description. */
	private String description;

	/** The edited. */
	private boolean edited = false;

	/**
	 * Instantiates a new fuel injection type representation.
	 */
	public FuelInjectionTypeRepresentation() {
		super();
	}

	/**
	 * Instantiates a new fuel injection type representation.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 * @param description the description
	 */
	public FuelInjectionTypeRepresentation(Long entityId, String label, String description) {

		this.entityId = entityId;
		this.label = label;
		this.description = description;
	}

	/**
	 * Instantiates a new fuel injection type representation.
	 * 
	 * @param entityId the entity id
	 */
	public FuelInjectionTypeRepresentation(Long entityId) {

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
