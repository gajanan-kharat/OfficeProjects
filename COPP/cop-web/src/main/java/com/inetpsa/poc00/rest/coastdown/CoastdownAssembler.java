package com.inetpsa.poc00.rest.coastdown;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.coastdown.CoastDown;

/**
 * The Class CoastdownAssembler.
 */
public class CoastdownAssembler extends BaseAssembler<CoastDown, CoastdownRepresentation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(CoastdownRepresentation targetDto, CoastDown sourceAggregate) {
		targetDto.setEntityId(sourceAggregate.getEntityId());
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(CoastDown targetAggregate, CoastdownRepresentation sourceDto) {

		targetAggregate.setpSAReference(sourceDto.getpSAReference());
		targetAggregate.setRoadLaw(sourceDto.getRoadLaw());
		targetAggregate.setTheoricalBenchF0(sourceDto.getTheoricalBenchF0());
		targetAggregate.setTheoricalBenchF1(sourceDto.getTheoricalBenchF1());
		targetAggregate.setTheoricalBenchF2(sourceDto.getTheoricalBenchF2());
		targetAggregate.setComputedBenchF0(sourceDto.getComputedBenchF0());
		targetAggregate.setComputedBenchF1(sourceDto.getComputedBenchF1());
		targetAggregate.setComputedBenchF2(sourceDto.getComputedBenchF2());
		targetAggregate.setVersion(sourceDto.getVersion());
		targetAggregate.setLatestversion(sourceDto.getLatestversion());
	}

}
