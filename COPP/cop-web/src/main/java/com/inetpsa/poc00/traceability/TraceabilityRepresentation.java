package com.inetpsa.poc00.traceability;

import java.util.Date;

/**
 * The Class TraceabilityRepresentation.
 */
public class TraceabilityRepresentation {

	/** The entity id. */
	protected Long entityId;

	/** The user id. */
	private String userId;

	/** The user profile. */
	private String userProfile;

	/** The description. */
	private String description;

	/** The new value. */
	private String newValue;

	/** The old value. */
	private String oldValue;

	/** The creation date. */
	private Date creationDate;

	/** The updation date. */
	private Date updationDate;

	/** The screen id. */
	private String screenId;

	/** The vehicle file id. */
	private Long vehicleFileId;

	/** The tvv entity id. */
	private Long tvvEntityId;

	/**
	 * Instantiates a new traceability representation.
	 */
	public TraceabilityRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new traceability representation.
	 *
	 * @param entityId the entity id
	 * @param userId the user id
	 * @param userProfile the user profile
	 * @param description the description
	 * @param newValue the new value
	 * @param oldvalue the oldvalue
	 * @param updationDate the updation date
	 */
	public TraceabilityRepresentation(Long entityId, String userId, String userProfile, String description, String newValue, String oldvalue, Date updationDate) {
		this.entityId = entityId;
		this.userId = userId;
		this.userProfile = userProfile;
		this.description = description;
		this.newValue = newValue;
		this.oldValue = oldvalue;
		this.updationDate = updationDate;
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
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the user profile.
	 * 
	 * @return the user profile
	 */
	public String getUserProfile() {
		return userProfile;
	}

	/**
	 * Sets the user profile.
	 * 
	 * @param userProfile the new user profile
	 */
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
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
	 * Gets the new value.
	 * 
	 * @return the new value
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * Sets the new value.
	 * 
	 * @param newValue the new new value
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
	 * Gets the old value.
	 * 
	 * @return the old value
	 */
	public String getOldValue() {
		return oldValue;
	}

	/**
	 * Sets the old value.
	 * 
	 * @param oldValue the new old value
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * Gets the creation date.
	 * 
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the updation date.
	 * 
	 * @return the updationDate
	 */
	public Date getUpdationDate() {
		return updationDate;
	}

	/**
	 * Sets the updation date.
	 * 
	 * @param updationDate the updationDate to set
	 */
	public void setUpdationDate(Date updationDate) {
		this.updationDate = updationDate;
	}

	/**
	 * Gets the screen id.
	 *
	 * @return the screenId
	 */
	public String getScreenId() {
		return screenId;
	}

	/**
	 * Sets the screen id.
	 *
	 * @param screenId the screenId to set
	 */
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	/**
	 * Gets the vehicle file id.
	 *
	 * @return the vehicle file id
	 */
	public Long getVehicleFileId() {
		return vehicleFileId;
	}

	/**
	 * Sets the vehicle file id.
	 *
	 * @param vehicleFileId the new vehicle file id
	 */
	public void setVehicleFileId(Long vehicleFileId) {
		this.vehicleFileId = vehicleFileId;
	}

	/**
	 * Getter tvvEntityId
	 * 
	 * @return the tvvEntityId
	 */
	public Long getTvvEntityId() {
		return tvvEntityId;
	}

	/**
	 * Setter tvvEntityId
	 * 
	 * @param tvvEntityId the tvvEntityId to set
	 */
	public void setTvvEntityId(Long tvvEntityId) {
		this.tvvEntityId = tvvEntityId;
	}
}
