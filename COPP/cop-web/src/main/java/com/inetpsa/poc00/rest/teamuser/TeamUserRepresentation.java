package com.inetpsa.poc00.rest.teamuser;

/**
 * The Class TeamUserRepresentation.
 */
public class TeamUserRepresentation {

	/** The entity id. */
	private Long teamEntityId;

	/** The user entity id. */
	private Long userEntityId;

	/** The team label. */
	private String teamLabel;

	/** The user id. */
	private String userId;

	/** The first last name. */
	private String firstLastName;
	/** The edited. */
	private boolean edited = false;

	/**
	 * Instantiates a new team user representation.
	 */
	TeamUserRepresentation() {
		super();

	}

	/**
	 * Instantiates a new team user representation.
	 *
	 * @param teamEntityId the team entity id
	 * @param teamLabel the team label
	 * @param userEntityId the user entity id
	 * @param userId the user id
	 * @param firstLastName the first last name
	 */
	public TeamUserRepresentation(Long teamEntityId, String teamLabel, Long userEntityId, String userId, String firstLastName) {
		super();
		this.teamEntityId = teamEntityId;
		this.userEntityId = userEntityId;
		this.teamLabel = teamLabel;
		this.userId = userId;
		this.firstLastName = firstLastName;
	}

	/**
	 * Gets the team label.
	 *
	 * @return the team label
	 */
	public String getTeamLabel() {
		return teamLabel;
	}

	/**
	 * Sets the team label.
	 *
	 * @param teamLabel the new team label
	 */
	public void setTeamLabel(String teamLabel) {
		this.teamLabel = teamLabel;
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
	 * Gets the first last name.
	 *
	 * @return the first last name
	 */
	public String getFirstLastName() {
		return firstLastName;
	}

	/**
	 * Sets the first last name.
	 *
	 * @param firstLastName the new first last name
	 */
	public void setFirstLastName(String firstLastName) {
		this.firstLastName = firstLastName;
	}

	/**
	 * Gets the team entity id.
	 *
	 * @return the team entity id
	 */
	public Long getTeamEntityId() {
		return teamEntityId;
	}

	/**
	 * Sets the team entity id.
	 *
	 * @param teamEntityId the new team entity id
	 */
	public void setTeamEntityId(Long teamEntityId) {
		this.teamEntityId = teamEntityId;
	}

	/**
	 * Gets the user entity id.
	 *
	 * @return the user entity id
	 */
	public Long getUserEntityId() {
		return userEntityId;
	}

	/**
	 * Sets the user entity id.
	 *
	 * @param userEntityId the new user entity id
	 */
	public void setUserEntityId(Long userEntityId) {
		this.userEntityId = userEntityId;
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
		return "TeamUserRepresentation [teamLabel=" + teamLabel + ", userId=" + userId + ", firstLastName=" + firstLastName + "]";
	}

}
