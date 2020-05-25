/*
 * Creation : Sep 12, 2016
 */
package com.inetpsa.poc00.domain.team;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class TeamFactoryDefault.
 */
public class TeamFactoryDefault extends BaseFactory<Team> implements TeamFactory {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.Team.TeamFactory#createTeam()
     */
    @Override
    public Team createTeam() {

        return new Team();
    }

}
