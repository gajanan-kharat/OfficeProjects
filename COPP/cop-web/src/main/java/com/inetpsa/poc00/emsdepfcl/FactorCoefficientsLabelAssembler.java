/*
 * Creation : Apr 26, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;


/**
 * The Class FactorCoefficientsLabelAssembler.
 */
public class FactorCoefficientsLabelAssembler extends BaseAssembler<FactorCoefficentsLabel, FactorCoefficentsLabelRepresntation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(FactorCoefficentsLabelRepresntation targetDto, FactorCoefficentsLabel sourceAggregate) {
		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setLabel(sourceAggregate.getLabel());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(FactorCoefficentsLabel targetAggregate, FactorCoefficentsLabelRepresntation sourceDto) {
		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setLabel(sourceDto.getLabel());

	}

}
