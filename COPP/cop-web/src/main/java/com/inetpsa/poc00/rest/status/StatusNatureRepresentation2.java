/*
 * Creation : Apr 28, 2016
 */
package com.inetpsa.poc00.rest.status;

/**
 * The Class StatusNatureRepresentation.
 */
public class StatusNatureRepresentation2 {

	/** The entity id. */
	private Long entityId;

	/** The flag. */
	private boolean flag = Boolean.FALSE;

	/**
	 * Instantiates a new status nature representation2.
	 *
	 * @param entityId the entity id
	 */
	public StatusNatureRepresentation2(Long entityId) {
		this.entityId = entityId;
		this.flag = false;
	}

	/**
	 * Instantiates a new status nature representation2.
	 */
	public StatusNatureRepresentation2() {
		super();
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Checks if is flag.
	 *
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * Sets the flag.
	 *
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
