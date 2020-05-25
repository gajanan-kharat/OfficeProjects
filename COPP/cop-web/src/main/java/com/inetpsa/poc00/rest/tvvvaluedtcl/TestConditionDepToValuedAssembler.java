/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtcl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictestconditionmandatory.GenericTestCMFactory;
import com.inetpsa.poc00.domain.unit.UnitFactory;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionAssembler;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionRepresentation;
import com.inetpsa.poc00.rest.unit.UnitAssembler;
import com.inetpsa.poc00.rest.unit.UnitRepresentation;

/**
 * The Class TestConditionDepToValuedAssembler.
 */
public class TestConditionDepToValuedAssembler extends BaseAssembler<GenericTestCondition, ValuedGenericConditionRepresentation> {

	/** The genereric test condition m andatory factory. */
	@Inject
	GenericTestCMFactory generericTestConditionMAndatoryFactory;

	/** The unit factory. */
	@Inject
	UnitFactory unitFactory;

	/** The unit assembler. */
	@Inject
	UnitAssembler unitAssembler;
	@Inject
	GenericTestConditionAssembler genericTestConditionAssembler;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(ValuedGenericConditionRepresentation targetDto, GenericTestCondition sourceEntity) {
		// targetDto.setEntityId(0);
		targetDto.setDataType(sourceEntity.getDataType());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());
		GenericTestConditionRepresentation genericCondition = new GenericTestConditionRepresentation();
		genericTestConditionAssembler.doAssembleDtoFromAggregate(genericCondition, sourceEntity);
		targetDto.setGenericCondition(genericCondition);
		UnitRepresentation unit = new UnitRepresentation();
		unit.setEntityId(0);
		unit.setValue(sourceEntity.getUnit().getValue());

		targetDto.setUnit(unit);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(GenericTestCondition targetEntity, ValuedGenericConditionRepresentation sourceDto) {
		// DO NOTHING
	}

}