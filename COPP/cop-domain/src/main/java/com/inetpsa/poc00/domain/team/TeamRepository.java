package com.inetpsa.poc00.domain.team;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface TeamRepository.
 */
public interface TeamRepository extends GenericRepository<Team, Long> {

	/**
	 * Save team.
	 * 
	 * @param team the team
	 * @return the team
	 */
	Team saveTeam(Team team);

	/**
	 * Persist team.
	 * 
	 * @param team the team
	 */
	void persistTeam(Team team);

	/**
	 * Delete all.
	 * 
	 * @return the int
	 */
	int deleteAll();

	/**
	 * Count.
	 * 
	 * @return the long
	 */
	long count();

	/**
	 * Gets the all teams.
	 * 
	 * @return the all teams
	 */
	List<Team> getAllTeams();

	/**
	 * Delete team.
	 * 
	 * @param id the id
	 * @return the int
	 */
	int deleteTeam(Long id);

	/**
	 * Gets the team by name.
	 * 
	 * @param teamLabel the team label
	 * @return the team by name
	 */
	Team getTeamByLabel(String teamLabel);

	/**
	 * Find or create team.
	 * 
	 * @param teamLabel the team label
	 * @return the team
	 */
	public Team findOrCreateTeam(String teamLabel);

}
