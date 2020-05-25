/*
 * Creation : Feb 29, 2016
 */
package com.inetpsa.poc00.infrastructure.data.generictc;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;

public class GenericTestCondtionAssembler extends
		BaseAssembler<GenericTestCondition, GenericTestConditionDto> {

	@Override
	protected void doAssembleDtoFromAggregate(
			GenericTestConditionDto targetDto, GenericTestCondition sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDataType(sourceEntity.getDataType());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());
		targetDto.setLabel(sourceEntity.getLabel());

	}

	@Override
	protected void doMergeAggregateWithDto(GenericTestCondition targetEntity,
			GenericTestConditionDto sourceDto) {
		sourceDto.setEntityId(targetEntity.getEntityId());
		sourceDto.setDataType(targetEntity.getDataType());
		sourceDto.setDefaultValue(targetEntity.getDefaultValue());
		sourceDto.setLabel(targetEntity.getLabel());
	}

}
