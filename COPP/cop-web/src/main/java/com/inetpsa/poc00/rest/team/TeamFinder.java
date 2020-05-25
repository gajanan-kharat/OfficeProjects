package com.inetpsa.poc00.rest.team;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.team.Team;
import com.inetpsa.poc00.rest.teamuser.TeamUserRepresentation;

/**
 * The Interface TeamFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TeamFinder {

	/**
	 * Gets the all teams.
	 *
	 * @return the all teams
	 */
	List<Team> getAllTeams();

	/**
	 * Gets the team by label.
	 *
	 * @param label the label
	 * @return the team by label
	 */
	List<Team> getTeamByLabel(String label);

	/**
	 * Gets the team by id.
	 *
	 * @param id the id
	 * @return the team by id
	 */
	Team getTeamById(long id);

	/**
	 * Gets the all team users.
	 *
	 * @return the all team users
	 */
	List<TeamUserRepresentation> getAllTeamUsers();
}
