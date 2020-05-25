/*
 * Creation : Jun 17, 2016
 */
package com.inetpsa.poc00.rest.valuedinertia;

/**
 * The Class ValuedInertiaRepresentation.
 */
public class ValuedInertiaRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The value. */
	private int value;

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
