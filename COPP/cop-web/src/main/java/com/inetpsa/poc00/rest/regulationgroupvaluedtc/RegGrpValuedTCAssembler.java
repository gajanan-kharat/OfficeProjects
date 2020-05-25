package com.inetpsa.poc00.rest.regulationgroupvaluedtc;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.rgvaluedtestcondition.RegGrpValdTestCondition;

/**
 * The Class RegGrpValuedTCAssembler.
 */
public class RegGrpValuedTCAssembler extends BaseAssembler<RegGrpValdTestCondition, RegGrpValdTestConditionRepresentation>{

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(
			RegGrpValdTestConditionRepresentation targetDto,
			RegGrpValdTestCondition sourceEntity) {
		
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDataType(sourceEntity.getDataType());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(RegGrpValdTestCondition targetEntity,
			RegGrpValdTestConditionRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setDataType(sourceDto.getDataType());
		targetEntity.setDefaultValue(sourceDto.getDefaultValue());
		
	}

}
