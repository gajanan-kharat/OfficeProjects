package com.inetpsa.poc00.rest.clasz;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.clasz.Clasz;

/**
 * The Class ClaszAssembler.
 */
public class ClaszAssembler extends BaseAssembler<Clasz, ClaszRepresentation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(ClaszRepresentation targetDto, Clasz sourceAggregate) {

		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setLabel(sourceAggregate.getLabel());
		targetDto.setDescription(sourceAggregate.getDescription());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(Clasz targetAggregate, ClaszRepresentation sourceDto) {

		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setLabel(sourceDto.getLabel());
		targetAggregate.setDescription(sourceDto.getDescription());

	}

}
