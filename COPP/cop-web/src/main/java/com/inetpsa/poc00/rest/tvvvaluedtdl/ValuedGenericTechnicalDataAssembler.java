/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtdl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictd.GenericTechDataFactory;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;
import com.inetpsa.poc00.domain.unit.Unit;
import com.inetpsa.poc00.domain.unit.UnitFactory;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataAssembler;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataRepresentation;
import com.inetpsa.poc00.rest.unit.UnitAssembler;
import com.inetpsa.poc00.rest.unit.UnitRepresentation;

public class ValuedGenericTechnicalDataAssembler extends BaseAssembler<TvvValuedTechData, ValuedGenericDataRepresentation> {
	/*	@Inject
		GenericTechDataMandatoryAssembler genericTechDataMandatoryAssembler;
		@Inject
		GenericTDMFactory genericTechDataMandatoryFactory;*/
	@Inject
	UnitFactory unitFactory;
	@Inject
	UnitAssembler unitAssembler;
	@Inject
	private GenericTechnicalDataAssembler genericTechnicalDataAssembler;
	@Inject
	private GenericTechDataFactory genericTechnicalDataFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(ValuedGenericDataRepresentation targetDto, TvvValuedTechData sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDataType(sourceEntity.getDataType());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDefaultValue(sourceEntity.getValue());
		if (sourceEntity.getGenericData() != null) {
			GenericTechnicalDataRepresentation genericData = new GenericTechnicalDataRepresentation();
			genericTechnicalDataAssembler.doAssembleDtoFromAggregate(genericData, sourceEntity.getGenericData());
			targetDto.setGenericData(genericData);
		}

		UnitRepresentation unit = new UnitRepresentation();
		unitAssembler.assembleDtoFromAggregate(unit, sourceEntity.getUnit());
		targetDto.setUnit(unit);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(TvvValuedTechData targetEntity, ValuedGenericDataRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDataType(sourceDto.getDataType());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setValue(sourceDto.getDefaultValue());
		if (sourceDto.getGenericData() != null) {
			GenericTechnicalData genericData = genericTechnicalDataFactory.createGenericTechData();
			genericTechnicalDataAssembler.doMergeAggregateWithDto(genericData, sourceDto.getGenericData());
			targetEntity.setGenericData(genericData);
		}
		Unit unit = unitFactory.createUnit();
		unitAssembler.mergeAggregateWithDto(unit, sourceDto.getUnit());
		targetEntity.setUnit(unit);

	}
}
