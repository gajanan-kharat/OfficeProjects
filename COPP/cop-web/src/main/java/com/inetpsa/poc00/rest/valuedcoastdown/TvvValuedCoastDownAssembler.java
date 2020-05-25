/*
 * Creation : Jun 17, 2016
 */
package com.inetpsa.poc00.rest.valuedcoastdown;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDown;
import com.inetpsa.poc00.domain.valuedinertia.ValuedInertia;
import com.inetpsa.poc00.domain.valuedinertia.ValuedInertiaFactory;
import com.inetpsa.poc00.rest.valuedinertia.ValuedInertiaRepresentation;

/**
 * The Class TvvValuedCoastDownAssembler.
 */
public class TvvValuedCoastDownAssembler extends BaseAssembler<TvvValuedCoastDown, ValuedCoastDownRepresentation> {

	/** The valued inertia factory. */
	@Inject
	private ValuedInertiaFactory valuedInertiaFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(ValuedCoastDownRepresentation targetDto, TvvValuedCoastDown sourceEntity) {
		targetDto.setpSAReference(sourceEntity.getpSAReference());
		targetDto.setRoadLaw(sourceEntity.getRoadLaw());
		targetDto.setTheoricalBenchF0(sourceEntity.getTheoricalBenchF0());
		targetDto.setTheoricalBenchF1(sourceEntity.getTheoricalBenchF1());
		targetDto.setTheoricalBenchF2(sourceEntity.getTheoricalBenchF2());
		targetDto.setComputedBenchF0(sourceEntity.getComputedBenchF0());
		targetDto.setComputedBenchF1(sourceEntity.getComputedBenchF1());
		targetDto.setComputedBenchF2(sourceEntity.getComputedBenchF2());
		targetDto.setUserBenchF0(sourceEntity.getUserBenchF0());
		targetDto.setUserBenchF1(sourceEntity.getUserBenchF1());
		targetDto.setUserBenchF2(sourceEntity.getUserBenchF2());
		targetDto.setVersion(sourceEntity.getVersion());
		targetDto.setEntityId(sourceEntity.getEntityId());
		ValuedInertiaRepresentation inertia = new ValuedInertiaRepresentation();
		inertia.setEntityId(sourceEntity.getInertia().getEntityId());
		inertia.setValue(sourceEntity.getInertia().getValue());
		targetDto.setInertia(inertia);
		targetDto.setInertia_value(sourceEntity.getInertia().getValue());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(TvvValuedCoastDown targetAggregate, ValuedCoastDownRepresentation sourceDto) {
		targetAggregate.setpSAReference(sourceDto.getpSAReference());
		targetAggregate.setRoadLaw(sourceDto.getRoadLaw());
		targetAggregate.setTheoricalBenchF0(sourceDto.getTheoricalBenchF0());
		targetAggregate.setTheoricalBenchF1(sourceDto.getTheoricalBenchF1());
		targetAggregate.setTheoricalBenchF2(sourceDto.getTheoricalBenchF2());
		targetAggregate.setComputedBenchF0(sourceDto.getComputedBenchF0());
		targetAggregate.setComputedBenchF1(sourceDto.getComputedBenchF1());
		targetAggregate.setComputedBenchF2(sourceDto.getComputedBenchF2());
		targetAggregate.setUserBenchF0(sourceDto.getUserBenchF0());
		targetAggregate.setUserBenchF1(sourceDto.getUserBenchF1());
		targetAggregate.setUserBenchF2(sourceDto.getUserBenchF2());
		targetAggregate.setVersion(sourceDto.getVersion());
		targetAggregate.setEntityId(0);

		ValuedInertia vIntertia = valuedInertiaFactory.createValuedInertia();
		vIntertia.setEntityId(sourceDto.getInertia().getEntityId());
		vIntertia.setValue(sourceDto.getInertia().getValue());
		targetAggregate.setInertia(vIntertia);

	}

}
