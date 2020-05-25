package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.team.Team;
import com.inetpsa.poc00.domain.team.TeamFactory;
import com.inetpsa.poc00.domain.team.TeamRepository;

/**
 * The Class TeamJpaRepository.
 */
public class TeamJpaRepository extends BaseJpaRepository<Team, Long> implements TeamRepository {

	/** The jpa entity manager. */
	@Inject
	private EntityManager jpaEntityManager;

	/** The team factory. */
	@Inject
	TeamFactory teamFactory;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(TeamJpaRepository.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.team.TeamRepository#deleteAll()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public int deleteAll() {
		try {

			return jpaEntityManager.createQuery("DELETE FROM Team t ").executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.team.TeamRepository#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.team.TeamRepository#saveTeam(com.inetpsa.poc00.domain.team.Team)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Team saveTeam(Team team) {
		if (team.getEntityId() == null || team.getEntityId() == 0)
			return super.save(team);

		return jpaEntityManager.merge(team);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.team.TeamRepository#persistTeam(com.inetpsa.poc00.domain.team.Team)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistTeam(Team team) {
		saveTeam(team);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.team.TeamRepository#getAllTeams()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<Team> getAllTeams() {
		TypedQuery<Team> query = jpaEntityManager.createQuery("SELECT t FROM Team t ", Team.class);

		return query.getResultList();

	}

	/**
	 * Gets the team by name.
	 *
	 * @param teamLabel the team label
	 * @return the team by name
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Team getTeamByLabel(String teamLabel) {
		TypedQuery<Team> query = jpaEntityManager.createQuery("SELECT t FROM Team t where t.label = ?1  ", Team.class);

		query.setParameter(1, teamLabel);

		List<Team> teamList = query.getResultList();

		if (teamList != null && !teamList.isEmpty())
			return teamList.get(0);

		return null;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.team.TeamRepository#deleteTeam(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public int deleteTeam(Long id) {

		try {

			return jpaEntityManager.createQuery("DELETE FROM Team t where t.entityId = " + id).executeUpdate();

		} catch (Exception e) {
			logger.info("in team delete--------------->");
			throw e;

		}

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.team.TeamRepository#findOrCreateTeam(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Team findOrCreateTeam(String teamLabel) {
		Team team = null;
		if (teamLabel != null) {
			team = getTeamByLabel(teamLabel);
			if (team == null) {
				team = teamFactory.createTeam();
				team.setLabel(teamLabel);
				team = saveTeam(team);
			}

		}
		return team;

	}
}
