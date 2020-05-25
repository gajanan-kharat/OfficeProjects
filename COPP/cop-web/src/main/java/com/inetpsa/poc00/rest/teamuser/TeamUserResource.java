package com.inetpsa.poc00.rest.teamuser;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.application.teamuser.TeamUserService;
import com.inetpsa.poc00.domain.team.Team;
import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.rest.team.TeamFinder;

/**
 * The Class TeamUserResource.
 */
@Path("/TeamUsersReference")
public class TeamUserResource {

	/** The team finder. */
	@Inject
	private TeamFinder teamFinder;

	/** The logger. */
	@Logging
	private Logger logger;

	/** The team user service. */
	@Inject
	private TeamUserService teamUserService;

	/**
	 * Gets the all team users.
	 * 
	 * @return the all team users
	 */
	@GET
	@Path("/AllTeamUsers")
	public Response getAllTeamUsers() {

		List<TeamUserRepresentation> teamRepresentationsList = teamFinder.getAllTeamUsers();

		logger.info("sending Teamusers value to UI");

		return Response.ok(teamRepresentationsList).build();
	}

	/**
	 * Save team users.
	 * 
	 * @param manageTeamUsersRequestDto the manage team users request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TeamUsers")
	public Response saveTeamUsers(ManageTeamUsersRequestDto manageTeamUsersRequestDto) {

		for (TeamUserRepresentation teamUser : manageTeamUsersRequestDto.getTeamUserRepresentationsList()) {

			Long tid = teamUser.getTeamEntityId();
			String tLabel = teamUser.getTeamLabel();
			Team team;
			if (tid != null && tid != 0) {
				team = teamUserService.setTeamIfExist(tLabel);
			}

			else {
				team = teamUserService.setTeamIfDoesntExist(tLabel);
				if (team != null) {

					teamUser.setTeamEntityId(team.getEntityId());
				}

			}

			Long uid = teamUser.getUserEntityId();

			if (uid != null) {
				teamUserService.setUserIfExist(uid, teamUser.getUserId(), teamUser.getFirstLastName(), team);

			} else {
				User updatedUser = teamUserService.setUserIfDoesntExist(uid, teamUser.getUserId(), teamUser.getFirstLastName(), team);
				teamUser.setUserEntityId(updatedUser.getEntityId());
			}
		}
		logger.info("Sucessfully saved teamUserRepresentation data");
		return Response.ok().build();

	}

	/**
	 * Delete team users.
	 *
	 * @param userEntityId the user entity id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/TeamUser/{entityId}")
	public Response deleteTeamUsers(@PathParam("entityId") String userEntityId) {
		try {

			teamUserService.deleteTeamUsers(userEntityId);
		} catch (Exception deletedteamUserException) {
		
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
		return Response.ok().build();

	}

}
