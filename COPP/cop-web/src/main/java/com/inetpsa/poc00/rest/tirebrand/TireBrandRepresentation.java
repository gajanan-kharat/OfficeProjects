package com.inetpsa.poc00.rest.tirebrand;

/**
 * The Class TireBrandRepresentation.
 */
public class TireBrandRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The label. */
	private String label;

	/** The edited. */
	private boolean edited = false;

	/**
	 * Instantiates a new tire brand representation.
	 */
	public TireBrandRepresentation() {
		super();

	}

	/**
	 * Instantiates a new tire brand representation.
	 *
	 * @param entityId the entity id
	 * @param label the label
	 */
	public TireBrandRepresentation(Long entityId, String label) {
		this.entityId = entityId;
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
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TireBrandRepresentation [label=" + label + "]";
	}

}
