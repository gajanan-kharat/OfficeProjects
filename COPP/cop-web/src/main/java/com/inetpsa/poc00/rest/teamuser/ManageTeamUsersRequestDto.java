package com.inetpsa.poc00.rest.teamuser;

import java.util.List;

/**
 * The Class ManageTeamUsersRequestDto.
 */
public class ManageTeamUsersRequestDto {

	/** The team user representations list. */
	private List<TeamUserRepresentation> teamUserRepresentationsList;

	/**
	 * Gets the team user representations list.
	 *
	 * @return the team user representations list
	 */
	public List<TeamUserRepresentation> getTeamUserRepresentationsList() {
		return teamUserRepresentationsList;
	}

	/**
	 * Sets the team user representations list.
	 *
	 * @param teamUserRepresentationsList the new team user representations list
	 */
	public void setTeamUserRepresentationsList(List<TeamUserRepresentation> teamUserRepresentationsList) {
		this.teamUserRepresentationsList = teamUserRepresentationsList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ManageTeamUsersRequestDto [teamUserRepresentationsList=" + teamUserRepresentationsList + "]";
	}

}
