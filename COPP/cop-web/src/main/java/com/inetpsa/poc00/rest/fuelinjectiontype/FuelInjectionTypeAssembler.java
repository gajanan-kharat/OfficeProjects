package com.inetpsa.poc00.rest.fuelinjectiontype;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;

/**
 * The Class FuelInjectionTypeAssembler.
 */
public class FuelInjectionTypeAssembler extends BaseAssembler<FuelInjectionType, FuelInjectionTypeRepresentation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(FuelInjectionTypeRepresentation targetDto, FuelInjectionType sourceAggregate) {

		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setDescription(sourceAggregate.getDescription());
		targetDto.setLabel(sourceAggregate.getLabel());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(FuelInjectionType targetAggregate, FuelInjectionTypeRepresentation sourceDto) {

		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setLabel(sourceDto.getLabel());
		targetAggregate.setDescription(sourceDto.getDescription());

	}
}
