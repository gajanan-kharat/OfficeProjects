/*
 * Creation : Feb 29, 2016
 */
package com.inetpsa.poc00.infrastructure.data.generictd;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;

/**
 * The Class GenericTechnicalDataAssembler.
 */
public class GenericTechnicalDataAssembler extends BaseAssembler<GenericTechnicalData, GenericTechnicalDataDto> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(GenericTechnicalDataDto targetDto, GenericTechnicalData sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDataType(sourceEntity.getDataType());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());
		targetDto.setLabel(sourceEntity.getLabel());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(GenericTechnicalData targetEntity, GenericTechnicalDataDto sourceDto) {
		sourceDto.setEntityId(targetEntity.getEntityId());
		sourceDto.setDataType(targetEntity.getDataType());
		sourceDto.setDefaultValue(targetEntity.getDefaultValue());
		sourceDto.setLabel(targetEntity.getLabel());
	}

}
