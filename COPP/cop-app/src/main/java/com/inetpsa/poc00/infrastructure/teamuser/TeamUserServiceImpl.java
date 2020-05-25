
package com.inetpsa.poc00.infrastructure.teamuser;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.teamuser.TeamUserService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.team.Team;
import com.inetpsa.poc00.domain.team.TeamFactory;
import com.inetpsa.poc00.domain.team.TeamRepository;
import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.user.UserFactory;
import com.inetpsa.poc00.domain.user.UserRepository;

/**
 * The Class TeamUserServiceImpl.
 */
public class TeamUserServiceImpl implements TeamUserService {

	/** The trace service. */
	@Inject
	private TraceabilityService traceService;

	/** The team repository. */
	@Inject
	private TeamRepository teamRepository;

	/** The user repository. */
	@Inject
	private UserRepository userRepository;

	/** The team factory. */
	@Inject
	private TeamFactory teamFactory;

	/** The user factory. */
	@Inject
	private UserFactory userFactory;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.teamuser.TeamUserService#deleteTeamUsers(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void deleteTeamUsers(String userEntityId) {
		try {
			Long id = Long.parseLong(userEntityId);

			User objToDelete = userRepository.load(id);

			Team team = objToDelete.getTeam();
			if (team != null) {

				List<User> userList = userRepository.getUsersByTeamId(team.getEntityId());
				if (userList.size() <= 1) {
					Team deletedTeam = teamRepository.load(team.getEntityId());
					teamRepository.delete(deletedTeam);
				}
			}
			userRepository.deleteUser(objToDelete.getEntityId());
		} catch (Exception deleteTeamUserException) {
			logger.error("error deleeting team user:{}", deleteTeamUserException);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.teamuser.TeamUserService#setTeamIfDoesntExist(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Team setTeamIfDoesntExist(String teamLabel) {
		Team team;
		logger.info("teamUser dont have teamEntityId------------->");
		team = teamRepository.findOrCreateTeam(teamLabel);
		traceService.historyForSave(team, ConstantsApp.SPECIFICCOP_SCREEN_ID);
		return team;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.teamuser.TeamUserService#setTeamIfExist(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Team setTeamIfExist(String teamLabel) {
		Team team;
		logger.info("teamUser have teamEntityId--------------->");

		if (teamLabel == null) {
			logger.info("team doesnt have label present---------------->");
			// Team update Case
			team = null;
		} else {
			logger.info("team  have label present----------->");

			team = teamRepository.findOrCreateTeam(teamLabel);

		}
		return team;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.teamuser.TeamUserService#setUserIfDoesntExist(java.lang.Long,
	 *      java.lang.String, java.lang.String, com.inetpsa.poc00.domain.team.Team)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public User setUserIfDoesntExist(Long entityId, String userId, String firstLastName, Team team) {
		logger.info("teamUser dont have userEntityId----------------->");
		return saveUser(entityId, userId, firstLastName, team);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.teamuser.TeamUserService#setUserIfExist(java.lang.Long,
	 *      com.inetpsa.poc00.domain.team.Team)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void setUserIfExist(Long entityId, String userId, String firstLastName, Team team) {
		User user;
		logger.info("teamUser have userEntityId----------------->");
		user = userRepository.load(entityId);
		User updatedUser = saveUser(user.getEntityId(), userId, firstLastName, team);
		traceService.historyForUpdate(user, updatedUser, ConstantsApp.SPECIFICCOP_SCREEN_ID);
	}

	/**
	 * Save user.
	 *
	 * @param entityId the entity id
	 * @param userId the user id
	 * @param firstLastName the first last name
	 * @param team the team
	 * @return the user
	 */
	private User saveUser(Long entityId, String userId, String firstLastName, Team team) {

		User user = null;
		if (entityId != null && entityId != 0) {
			user = userRepository.load(entityId);
		}
		if (user == null) {
			user = userFactory.createUser();
		}

		user.setUserId(userId);
		user.setFirstName(firstLastName);
		if (team != null)
			user.setTeam(team);
		user = userRepository.saveUser(user);
		return user;

	}

}
