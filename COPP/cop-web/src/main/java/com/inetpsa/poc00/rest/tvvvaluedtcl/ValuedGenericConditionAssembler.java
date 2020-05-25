/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtcl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionFactory;
import com.inetpsa.poc00.domain.generictestconditionmandatory.GenericTestCMFactory;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition;
import com.inetpsa.poc00.domain.unit.Unit;
import com.inetpsa.poc00.domain.unit.UnitFactory;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionAssembler;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionRepresentation;
import com.inetpsa.poc00.rest.unit.UnitAssembler;
import com.inetpsa.poc00.rest.unit.UnitRepresentation;

public class ValuedGenericConditionAssembler extends BaseAssembler<TvvValuedTestCondition, ValuedGenericConditionRepresentation> {

	@Inject
	GenericTestCMFactory generericTestConditionMAndatoryFactory;
	@Inject
	UnitFactory unitFactory;
	@Inject
	UnitAssembler unitAssembler;
	@Inject
	private GenericTestConditionAssembler genericTestConditionAssembler;
	@Inject
	GenericTestConditionFactory genericTestConditionFactory;

	@Override
	public void doAssembleDtoFromAggregate(ValuedGenericConditionRepresentation targetDto, TvvValuedTestCondition sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDataType(sourceEntity.getDataType());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());
		if (sourceEntity.getGenericCondition() != null) {
			GenericTestConditionRepresentation genericCondition = new GenericTestConditionRepresentation();
			genericTestConditionAssembler.doAssembleDtoFromAggregate(genericCondition, sourceEntity.getGenericCondition());
			targetDto.setGenericCondition(genericCondition);
		}

		UnitRepresentation unit = new UnitRepresentation();
		unitAssembler.doAssembleDtoFromAggregate(unit, sourceEntity.getUnit());
		targetDto.setUnit(unit);

	}

	@Override
	public void doMergeAggregateWithDto(TvvValuedTestCondition targetEntity, ValuedGenericConditionRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDataType(sourceDto.getDataType());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setDefaultValue(sourceDto.getDefaultValue());
		if (sourceDto.getGenericCondition() != null) {
			GenericTestCondition genericCondition = genericTestConditionFactory.createGenericTestCondtn();
			genericTestConditionAssembler.doMergeAggregateWithDto(genericCondition, sourceDto.getGenericCondition());
			targetEntity.setGenericCondition(genericCondition);
		}

		Unit unit = unitFactory.createUnit();
		unitAssembler.doMergeAggregateWithDto(unit, sourceDto.getUnit());
		targetEntity.setUnit(unit);

	}

}
