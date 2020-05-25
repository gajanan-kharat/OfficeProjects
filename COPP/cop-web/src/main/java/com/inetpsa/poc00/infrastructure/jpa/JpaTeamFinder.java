/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.team.Team;
import com.inetpsa.poc00.rest.team.TeamFinder;
import com.inetpsa.poc00.rest.teamuser.TeamUserRepresentation;

/**
 * The Class JpaTeamFinder.
 */
public class JpaTeamFinder implements TeamFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.team.TeamFinder#getAllTeams()
	 */
	@Override
	public List<Team> getAllTeams() {

		return entityManager.createQuery("SELECT tb FROM Team tb ").getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.team.TeamFinder#getTeamByLabel(java.lang.String)
	 */
	@Override
	public List<Team> getTeamByLabel(String label) {

		return entityManager.createQuery("SELECT tb FROM Team tb where tb.label=:plabel").setParameter("plabel", label).getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.team.TeamFinder#getTeamById(long)
	 */
	@Override
	public Team getTeamById(long id) {

		return (Team) entityManager.createQuery("SELECT tb FROM Team tb where tb.entityId=:id").setParameter("id", id).getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.team.TeamFinder#getAllTeamUsers()
	 */
	@Override
	public List<TeamUserRepresentation> getAllTeamUsers() {

		TypedQuery<TeamUserRepresentation> query = entityManager.createQuery("select new " + TeamUserRepresentation.class.getName() + "(COALESCE(u.team.entityId,'0'), COALESCE(u.team.label,''),u.entityId ,u.userId,  concat(' ',COALESCE(u.firstName,''),COALESCE(u.lastName,'')) as firstLastName)" + " from User u LEFT JOIN u.team t  ", TeamUserRepresentation.class);

		return query.getResultList();

	}
}