
package com.inetpsa.poc00.application.teamuser;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.team.Team;
import com.inetpsa.poc00.domain.user.User;

/**
 * The Interface TeamUserService.
 */
@Service
public interface TeamUserService {

	/**
	 * Delete team users.
	 *
	 * @param userEntityId the user entity id
	 */
	void deleteTeamUsers(String userEntityId);

	/**
	 * Sets the team if doesnt exist.
	 *
	 * @param teamLabel the team label
	 * @return the team
	 */
	Team setTeamIfDoesntExist(String teamLabel);

	/**
	 * Sets the team if exist.
	 *
	 * @param teamLabel the team label
	 * @return the team
	 */
	Team setTeamIfExist(String teamLabel);

	/**
	 * Sets the user if doesnt exist.
	 *
	 * @param entityId the entity id
	 * @param userId the user id
	 * @param firstLastName the first last name
	 * @param team the team
	 * @return the user
	 */
	User setUserIfDoesntExist(Long entityId, String userId, String firstLastName, Team team);

	/**
	 * Sets the user if exist.
	 *
	 * @param entityId the entity id
	 * @param userId the user id
	 * @param firstLastName the first last name
	 * @param team the team
	 */
	void setUserIfExist(Long entityId, String userId, String firstLastName, Team team);
}
