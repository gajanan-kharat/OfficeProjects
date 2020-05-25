package com.inetpsa.poc00.traceability;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.traceability.Traceability;

/**
 * The Class TraceabilityAssembler.
 */
public class TraceabilityAssembler extends BaseAssembler<Traceability, TraceabilityDto> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TraceabilityDto targetDto, Traceability sourceAggregate) {

		
		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setDescription(sourceAggregate.getDescription());
		targetDto.setNewValue(sourceAggregate.getNewValue());
		targetDto.setOldValue(sourceAggregate.getOldValue());
		targetDto.setUserId(sourceAggregate.getUserId());
		targetDto.setUserProfile(sourceAggregate.getUserProfile());
		targetDto.setScreenId(sourceAggregate.getScreenId());
		
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(Traceability targetAggregate, TraceabilityDto sourceDto) {

		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setDescription(sourceDto.getDescription());
		targetAggregate.setNewValue(sourceDto.getNewValue());
		targetAggregate.setOldValue(sourceDto.getOldValue());
		targetAggregate.setUserId(sourceDto.getUserId());
		targetAggregate.setUserProfile(sourceDto.getUserProfile());
		targetAggregate.setScreenId(sourceDto.getScreenId());
		
	}

}
