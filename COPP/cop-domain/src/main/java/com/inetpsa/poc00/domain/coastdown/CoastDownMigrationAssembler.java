package com.inetpsa.poc00.domain.coastdown;

import org.seedstack.business.assembler.BaseAssembler;

/**
 * The Class CoastDownMigrationAssembler.
 */
public class CoastDownMigrationAssembler extends BaseAssembler<CoastDown, CoastDownMigrationDto> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(CoastDownMigrationDto targetDto, CoastDown sourceAggregate) {
		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setInertiaId(sourceAggregate.getInertia().getEntityId());
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(CoastDown targetAggregate, CoastDownMigrationDto sourceDto) {

		targetAggregate.setpSAReference(sourceDto.getpSAReference());
		targetAggregate.setRoadLaw(sourceDto.getRoadLaw());
		targetAggregate.setVersion(sourceDto.getVersion());
		targetAggregate.setTheoricalBenchF0(sourceDto.getTheoricalBenchF0());
		targetAggregate.setTheoricalBenchF1(sourceDto.getTheoricalBenchF1());
		targetAggregate.setTheoricalBenchF2(sourceDto.getTheoricalBenchF2());
		targetAggregate.setComputedBenchF0(sourceDto.getComputedBenchF0());
		targetAggregate.setComputedBenchF1(sourceDto.getComputedBenchF1());
		targetAggregate.setComputedBenchF2(sourceDto.getComputedBenchF2());
		targetAggregate.setLatestversion(Boolean.FALSE);
		
	}

}
