package com.inetpsa.poc00.rest.regulationgroupvaluedtc;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;

/**
 * The Class GenericTCtoRGVAluedTC.
 */
public class GenericTCtoRGVAluedTC extends BaseAssembler<GenericTestCondition, RegGrpValdTestConditionRepresentation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(RegGrpValdTestConditionRepresentation targetDto, GenericTestCondition sourceEntity) {
		targetDto.setEntityId(0);
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDataType(sourceEntity.getDataType());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(GenericTestCondition arg0, RegGrpValdTestConditionRepresentation arg1) {
		// DO NOTHING

	}

}
