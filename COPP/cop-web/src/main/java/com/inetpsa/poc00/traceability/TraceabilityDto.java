package com.inetpsa.poc00.traceability;

/**
 * The Class TraceabilityDto.
 */
public class TraceabilityDto {

	/** The entity id. */
	private Long entityId;
	
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
	
	/** The screen id. */
	private String screenId;

	/**
	 * Instantiates a new traceability dto.
	 */
	public TraceabilityDto() {
		super();
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

}
