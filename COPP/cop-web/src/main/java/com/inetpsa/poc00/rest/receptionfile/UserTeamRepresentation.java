package com.inetpsa.poc00.rest.receptionfile;

import com.inetpsa.poc00.rest.team.TeamRepresentation;
import com.inetpsa.poc00.rest.user.UserRepresentation;

/**
 * The Class UserTeamRepresentation.
 */
public class UserTeamRepresentation {

	/** The publishing user representation. */
	private UserRepresentation publishingUserRepresentation;

	/** The publishing team representation. */
	private TeamRepresentation publishingTeamRepresentation;

	/**
	 * Instantiates a new user team representation.
	 *
	 * @param publishingUserRepresentation the publishing user representation
	 * @param publishingTeamRepresentation the publishing team representation
	 */
	public UserTeamRepresentation(UserRepresentation publishingUserRepresentation, TeamRepresentation publishingTeamRepresentation) {
		super();
		this.publishingUserRepresentation = publishingUserRepresentation;
		this.publishingTeamRepresentation = publishingTeamRepresentation;
	}

	/**
	 * Gets the publishing user representation.
	 *
	 * @return the publishing user representation
	 */
	public UserRepresentation getPublishingUserRepresentation() {
		return publishingUserRepresentation;
	}

	/**
	 * Sets the publishing user representation.
	 *
	 * @param publishingUserRepresentation the new publishing user representation
	 */
	public void setPublishingUserRepresentation(UserRepresentation publishingUserRepresentation) {
		this.publishingUserRepresentation = publishingUserRepresentation;
	}

	/**
	 * Gets the publishing team representation.
	 *
	 * @return the publishing team representation
	 */
	public TeamRepresentation getPublishingTeamRepresentation() {
		return publishingTeamRepresentation;
	}

	/**
	 * Sets the publishing team representation.
	 *
	 * @param publishingTeamRepresentation the new publishing team representation
	 */
	public void setPublishingTeamRepresentation(TeamRepresentation publishingTeamRepresentation) {
		this.publishingTeamRepresentation = publishingTeamRepresentation;
	}

}