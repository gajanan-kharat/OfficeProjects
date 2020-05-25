package com.inetpsa.poc00.rest.team;

import java.util.List;

import com.inetpsa.poc00.rest.user.UserRepresentation;

/**
 * The Class TeamRepresentation.
 */
public class TeamRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The label. */
	private String label;

	/** The edited. */
	private boolean edited = false;

	/** The user list. */
	private List<UserRepresentation> userList;

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

	/**
	 * Gets the user list.
	 *
	 * @return the user list
	 */
	public List<UserRepresentation> getUserList() {
		return userList;
	}

	/**
	 * Sets the user list.
	 *
	 * @param userList the new user list
	 */
	public void setUserList(List<UserRepresentation> userList) {
		this.userList = userList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TeamRepresentation [label=" + label + ", userList=" + userList + "]";
	}

}
