package com.inetpsa.poc00.rest.factorcoeffapptype;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;


/**
 * The Class FactorCoeffAppTypeAssembler.
 */
public class FactorCoeffAppTypeAssembler extends BaseAssembler<FactorCoeffApplicationType, FactorCoeffAppTypeRepresentation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(FactorCoeffAppTypeRepresentation targetDto, FactorCoeffApplicationType sourceAggregate) {

		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setLabel(sourceAggregate.getLabel());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(FactorCoeffApplicationType targetAggregate, FactorCoeffAppTypeRepresentation sourceDto) {

		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setLabel(sourceDto.getLabel());

	}

}
