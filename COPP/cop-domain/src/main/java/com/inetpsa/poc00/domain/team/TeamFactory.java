/*
 * Creation : Sep 12, 2016
 */
package com.inetpsa.poc00.domain.team;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating Team objects.
 */
public interface TeamFactory extends GenericFactory<Team> {

	/**
	 * Creates a new Team object.
	 *
	 * @return the team
	 */
	Team createTeam();
}
