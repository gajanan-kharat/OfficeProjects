package com.inetpsa.poc00.rest.factorcoeffapptype;

/**
 * The Class FactorCoeffAppTypeRepresentation.
 */
public class FactorCoeffAppTypeRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The label. */
	private String label;

	/** The edited. */
	private boolean edited = false;

	/**
	 * Instantiates a new factor coeff app type representation.
	 */
	public FactorCoeffAppTypeRepresentation() {
		super();
	}

	/**
	 * Instantiates a new factor coeff app type representation.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 */
	public FactorCoeffAppTypeRepresentation(Long entityId, String label) {

		this.entityId = entityId;
		this.label = label;
	}

	/**
	 * Instantiates a new factor coeff app type representation.
	 * 
	 * @param entityId the entity id
	 */
	public FactorCoeffAppTypeRepresentation(Long entityId) {

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
