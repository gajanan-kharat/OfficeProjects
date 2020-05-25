/*
 * Creation : Apr 19, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;

/**
 * The Class FactorCoeffAppTypeAssembler.
 */
public class FactorCoeffAppTypeAssembler extends BaseAssembler<FactorCoeffApplicationType, FactorCoeffApplicationTypeRepresentation> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(FactorCoeffApplicationTypeRepresentation fcAppTypeRepresentation, FactorCoeffApplicationType fcAppType) {
		fcAppTypeRepresentation.setEntityId(fcAppType.getEntityId());
		fcAppTypeRepresentation.setLabel(fcAppType.getLabel());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(FactorCoeffApplicationType fcAppType, FactorCoeffApplicationTypeRepresentation fcAppTypeRepresentation) {

		fcAppType.setEntityId(fcAppTypeRepresentation.getEntityId());
		fcAppType.setLabel(fcAppTypeRepresentation.getLabel());
	}

}
