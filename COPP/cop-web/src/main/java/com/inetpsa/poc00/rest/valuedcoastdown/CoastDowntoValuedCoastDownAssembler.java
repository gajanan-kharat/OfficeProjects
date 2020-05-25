/*
 * Creation : Jun 17, 2016
 */
package com.inetpsa.poc00.rest.valuedcoastdown;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDown;
import com.inetpsa.poc00.domain.valuedinertia.ValuedInertia;
import com.inetpsa.poc00.domain.valuedinertia.ValuedInertiaFactory;
import com.inetpsa.poc00.rest.coastdown.CoastdownRepresentation;

/**
 * The Class CoastDowntoValuedCoastDownAssembler.
 */
public class CoastDowntoValuedCoastDownAssembler extends BaseAssembler<TvvValuedCoastDown, CoastdownRepresentation> {

	/** The valued inertia fctory. */
	@Inject
	private ValuedInertiaFactory valuedInertiaFctory;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(CoastdownRepresentation arg0, TvvValuedCoastDown arg1) {
		// DO NOTHING
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(TvvValuedCoastDown targetAggregate, CoastdownRepresentation sourceDto) {
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

		ValuedInertia vIntertia = valuedInertiaFctory.createValuedInertia();
		vIntertia.setEntityId(0);
		vIntertia.setValue(sourceDto.getInertia_value());
		targetAggregate.setInertia(vIntertia);
	}
}
