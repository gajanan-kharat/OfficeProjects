package com.inetpsa.poc00.rest.user;

import java.util.List;

/**
 * The Class ManageTeamRequestDto.
 */
public class ManageUserRequestDto {

	/** The user representations list. */
	private List<UserRepresentation> userRepresentationsList;

	/**
	 * Gets the user representations list.
	 *
	 * @return the user representations list
	 */
	public List<UserRepresentation> getUserRepresentationsList() {
		return userRepresentationsList;
	}

	/**
	 * Sets the user representations list.
	 *
	 * @param userRepresentationsList the new user representations list
	 */
	public void setUserRepresentationsList(List<UserRepresentation> userRepresentationsList) {
		this.userRepresentationsList = userRepresentationsList;
	}

}
