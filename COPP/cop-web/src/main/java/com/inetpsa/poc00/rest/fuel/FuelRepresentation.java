package com.inetpsa.poc00.rest.fuel;

/**
 * The Class FuelRepresentation.
 */
public class FuelRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The label. */
	private String label;

	/** The description. */
	private String description;

	/** The default fuel. */
	private boolean defaultFuel;

	/** The fuel type_ id. */
	private Long fuelTypeId;

	/** The fuel type_label. */
	private String fuelTypelabel;

	/** The edited. */
	private boolean edited = false;

	/**
	 * Instantiates a new fuel representation.
	 */
	public FuelRepresentation() {
		super();
	}

	/**
	 * Instantiates a new fuel representation.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 * @param description the description
	 * @param defaultFuel the default fuel
	 * @param fuelType_Id the fuel type id
	 * @param fuelTypelabel the fuel type label
	 */
	public FuelRepresentation(Long entityId, String label, String description, boolean defaultFuel, Long fuelTypeId, String fuelTypelabel) {

		this.entityId = entityId;
		this.label = label;
		this.description = description;
		this.defaultFuel = defaultFuel;
		this.fuelTypeId = fuelTypeId;
		this.fuelTypelabel = fuelTypelabel;
	}

	/**
	 * Instantiates a new fuel representation.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 * @param description the description
	 * @param defaultFuel the default fuel
	 */
	public FuelRepresentation(Long entityId, String label, String description, boolean defaultFuel) {

		this.entityId = entityId;
		this.label = label;
		this.description = description;
		this.defaultFuel = defaultFuel;

	}

	/**
	 * Instantiates a new fuel representation.
	 * 
	 * @param entityId the entity id
	 */
	public FuelRepresentation(Long entityId) {

		this.entityId = entityId;

	}

	/**
	 * Instantiates a new fuel representation.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 */
	public FuelRepresentation(Long entityId, String label) {

		this.entityId = entityId;
		this.label = label;

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
	 * Checks if is default fuel.
	 * 
	 * @return true, if is default fuel
	 */
	public boolean isDefaultFuel() {
		return defaultFuel;
	}

	/**
	 * Sets the default fuel.
	 * 
	 * @param defaultFuel the new default fuel
	 */
	public void setDefaultFuel(boolean defaultFuel) {
		this.defaultFuel = defaultFuel;
	}

	public Long getFuelTypeId() {
		return fuelTypeId;
	}

	public void setFuelTypeId(Long fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}

	public String getFuelTypelabel() {
		return fuelTypelabel;
	}

	public void setFuelTypelabel(String fuelTypelabel) {
		this.fuelTypelabel = fuelTypelabel;
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
