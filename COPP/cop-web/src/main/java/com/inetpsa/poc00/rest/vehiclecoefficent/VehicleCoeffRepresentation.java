package com.inetpsa.poc00.rest.vehiclecoefficent;

/**
 * The Class VehicleCoeffRepresentation.
 */
public class VehicleCoeffRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The label. */
	private String label;

	/** The description. */
	private String description;

	/** The f0 coeffiecient. */
	private double f0Coeffiecient;

	/** The f1 coeffiecient. */
	private double f1Coeffiecient;

	/** The f2 coeffiecient. */
	private double f2Coeffiecient;

	/** The edited. */
	private boolean edited = false;

	/**
	 * Instantiates a new vehicle coeff representation.
	 */
	public VehicleCoeffRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new vehicle coeff representation.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 * @param description the description
	 * @param f0Coeffiecient the f0 coeffiecient
	 * @param f1Coeffiecient the f1 coeffiecient
	 * @param f2Coeffiecient the f2 coeffiecient
	 */
	public VehicleCoeffRepresentation(Long entityId, String label, String description, double f0Coeffiecient, double f1Coeffiecient, double f2Coeffiecient) {

		this.entityId = entityId;
		this.label = label;
		this.description = description;
		this.f0Coeffiecient = f0Coeffiecient;
		this.f1Coeffiecient = f1Coeffiecient;
		this.f2Coeffiecient = f2Coeffiecient;
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
	 * Gets the f0 coeffiecient.
	 * 
	 * @return the f0 coeffiecient
	 */
	public double getF0Coeffiecient() {
		return f0Coeffiecient;
	}

	/**
	 * Sets the f0 coeffiecient.
	 * 
	 * @param f0Coeffiecient the new f0 coeffiecient
	 */
	public void setF0Coeffiecient(double f0Coeffiecient) {
		this.f0Coeffiecient = f0Coeffiecient;
	}

	/**
	 * Gets the f1 coeffiecient.
	 * 
	 * @return the f1 coeffiecient
	 */
	public double getF1Coeffiecient() {
		return f1Coeffiecient;
	}

	/**
	 * Sets the f1 coeffiecient.
	 * 
	 * @param f1Coeffiecient the new f1 coeffiecient
	 */
	public void setF1Coeffiecient(double f1Coeffiecient) {
		this.f1Coeffiecient = f1Coeffiecient;
	}

	/**
	 * Gets the f2 coeffiecient.
	 * 
	 * @return the f2 coeffiecient
	 */
	public double getF2Coeffiecient() {
		return f2Coeffiecient;
	}

	/**
	 * Sets the f2 coeffiecient.
	 * 
	 * @param f2Coeffiecient the new f2 coeffiecient
	 */
	public void setF2Coeffiecient(double f2Coeffiecient) {
		this.f2Coeffiecient = f2Coeffiecient;
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
