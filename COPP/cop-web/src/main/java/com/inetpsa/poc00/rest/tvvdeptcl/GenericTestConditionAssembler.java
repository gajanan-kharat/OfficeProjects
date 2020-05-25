/*
 * Creation : Apr 1, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictestconditionmandatory.GenericTestCMFactory;
import com.inetpsa.poc00.domain.generictestconditionmandatory.GenericTestConditionMandatory;
import com.inetpsa.poc00.domain.unit.Unit;
import com.inetpsa.poc00.domain.unit.UnitFactory;
import com.inetpsa.poc00.rest.unit.UnitAssembler;
import com.inetpsa.poc00.rest.unit.UnitRepresentation;

/**
 * The Class GenericTestConditionAssembler.
 */
public class GenericTestConditionAssembler extends BaseAssembler<GenericTestCondition, GenericTestConditionRepresentation> {
	
	/** The generic condition mandatory assembler. */
	@Inject
	GenericConditionMandatoryAssembler genericConditionMandatoryAssembler;
	
	/** The genereric test condition M andatory factory. */
	@Inject
	GenericTestCMFactory generericTestConditionMAndatoryFactory;
	
	/** The unit factory. */
	@Inject
	UnitFactory unitFactory;
	
	/** The unit assembler. */
	@Inject
	UnitAssembler unitAssembler;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(GenericTestConditionRepresentation targetDto, GenericTestCondition sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDataType(sourceEntity.getDataType());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());
		UnitRepresentation unit = new UnitRepresentation();
		unitAssembler.assembleDtoFromAggregate(unit, sourceEntity.getUnit());
		targetDto.setUnit(unit);
		if (sourceEntity.getGnericTestConditionMandatory() != null && !sourceEntity.getGnericTestConditionMandatory().isEmpty()) {
			List<GenericTestConditionMandatory> dataItems = sourceEntity.getGnericTestConditionMandatory();
			List<GenericConditionMandatoryRepresentation> dataList = new ArrayList<>();
			for (GenericTestConditionMandatory dataItem : dataItems) {
				GenericConditionMandatoryRepresentation data = new GenericConditionMandatoryRepresentation();
				genericConditionMandatoryAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setGenericTestCondition(targetDto);
				dataList.add(data);

			}
			targetDto.setGenericConditionMandatory(dataList);
		}
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(GenericTestCondition targetEntity, GenericTestConditionRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDataType(sourceDto.getDataType());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setDefaultValue(sourceDto.getDefaultValue());
		targetEntity.setIsDeleted(sourceDto.getIsDeleted());
		Unit unit = unitFactory.createUnit();
		unitAssembler.mergeAggregateWithDto(unit, sourceDto.getUnit());
		targetEntity.setUnit(unit);
		if (sourceDto.getGenericConditionMandatory() != null && !sourceDto.getGenericConditionMandatory().isEmpty()) {
			List<GenericConditionMandatoryRepresentation> dataItems = sourceDto.getGenericConditionMandatory();
			List<GenericTestConditionMandatory> dataList = new ArrayList<>();
			for (GenericConditionMandatoryRepresentation dataItem : dataItems) {
				GenericTestConditionMandatory data = generericTestConditionMAndatoryFactory.createGenericTestCM();
				genericConditionMandatoryAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setGenericTestCondition(targetEntity);
				dataList.add(data);

			}
			targetEntity.setGnericTestConditionMandatory(dataList);
		}
	}

}
