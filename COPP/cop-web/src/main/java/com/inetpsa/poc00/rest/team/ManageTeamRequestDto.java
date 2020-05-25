package com.inetpsa.poc00.rest.team;

import java.util.List;

/**
 * The Class ManageTeamRequestDto.
 */
public class ManageTeamRequestDto {

	/** The team representations list. */
	private List<TeamRepresentation> teamRepresentationsList;

	/**
	 * Gets the team representations list.
	 *
	 * @return the team representations list
	 */
	public List<TeamRepresentation> getTeamRepresentationsList() {
		return teamRepresentationsList;
	}

	/**
	 * Sets the team representations list.
	 *
	 * @param teamRepresentationsList the new team representations list
	 */
	public void setTeamRepresentationsList(List<TeamRepresentation> teamRepresentationsList) {
		this.teamRepresentationsList = teamRepresentationsList;
	}

}
