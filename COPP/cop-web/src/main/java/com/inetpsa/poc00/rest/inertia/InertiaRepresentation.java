package com.inetpsa.poc00.rest.inertia;

/**
 * The Class InertiaRepresentation.
 */
public class InertiaRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The value. */
	private int value;

	/**
	 * Instantiates a new inertia representation.
	 */
	public InertiaRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new inertia representation.
	 *
	 * @param entityId the entity id
	 * @param value the value
	 */
	public InertiaRepresentation(Long entityId, int value) {

		this.entityId = entityId;
		this.value = value;
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
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(int value) {
		this.value = value;
	}

}
