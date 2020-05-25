package com.inetpsa.poc00.rest.user;

/**
 * The Class UserRepresentation.
 */
public class UserRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The user id. */
	private String userId;

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The edited. */
	private boolean edited = false;

	/**
	 * Instantiates a new user representation.
	 */
	public UserRepresentation() {
		super();
	}

	/**
	 * Instantiates a new user representation.
	 *
	 * @param userID the user id
	 * @param firstName the first name
	 * @param lastName the last name
	 */
	public UserRepresentation(String userID, String firstName, String lastName) {
		super();
		this.userId = userID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Instantiates a new user representation.
	 *
	 * @param entityId the entity id
	 * @param userID the user id
	 * @param firstName the first name
	 * @param lastName the last name
	 */
	public UserRepresentation(Long entityId, String userID, String firstName, String lastName) {
		super();
		this.entityId = entityId;
		this.userId = userID;
		this.firstName = firstName;
		this.lastName = lastName;
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
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserRepresentation [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
