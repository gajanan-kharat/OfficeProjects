package com.inetpsa.poc00.rest.team;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.team.Team;

/**
 * The Class TeamAssembler.
 */
public class TeamAssembler extends BaseAssembler<Team, TeamRepresentation> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(TeamRepresentation targetDto, Team sourceEntity) {

		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setLabel(sourceEntity.getLabel());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(Team targetEntity, TeamRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setLabel(sourceDto.getLabel());

	}

}
