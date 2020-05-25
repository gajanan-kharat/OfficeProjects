package com.inetpsa.poc00.rest.regulationgroupvaluedes;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;

/**
 * The Class EmsDepTCtoRGValuedTCAssembler.
 */
public class EmsDepTCtoRGValuedTCAssembler extends BaseAssembler<EmissionStdDepTCL, RGValuedESDependentTCLRepresentation> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(RGValuedESDependentTCLRepresentation targetDto, EmissionStdDepTCL sourceEntity) {
		targetDto.setEntityId(0);
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setVersion(sourceEntity.getVersion());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(EmissionStdDepTCL arg0, RGValuedESDependentTCLRepresentation arg1) {
		// DO NOTHING
	}

}
