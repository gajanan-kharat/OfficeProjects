/*
 * Creation : Mar 23, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.generictechdatamandatory.GenericTDMFactory;
import com.inetpsa.poc00.domain.generictechdatamandatory.GenericTechDataMandatory;
import com.inetpsa.poc00.domain.unit.Unit;
import com.inetpsa.poc00.domain.unit.UnitFactory;
import com.inetpsa.poc00.rest.unit.UnitAssembler;
import com.inetpsa.poc00.rest.unit.UnitRepresentation;

/**
 * The Class GenericTechnicalDataAssembler.
 */
public class GenericTechnicalDataAssembler extends BaseAssembler<GenericTechnicalData, GenericTechnicalDataRepresentation> {

	/** The generic tech data mandatory assembler. */
	@Inject
	GenericTechDataMandatoryAssembler genericTechDataMandatoryAssembler;
	
	/** The generic tech data mandatory factory. */
	@Inject
	GenericTDMFactory genericTechDataMandatoryFactory;
	
	/** The unit factory. */
	@Inject
	UnitFactory unitFactory;
	
	/** The unit assembler. */
	@Inject
	UnitAssembler unitAssembler;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(GenericTechnicalDataRepresentation targetDto, GenericTechnicalData sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDataType(sourceEntity.getDataType());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());
		UnitRepresentation unit = new UnitRepresentation();
		unitAssembler.assembleDtoFromAggregate(unit, sourceEntity.getUnit());
		targetDto.setUnit(unit);
		if (sourceEntity.getGenericTechnicalDataManadatory() != null && !sourceEntity.getGenericTechnicalDataManadatory().isEmpty()) {
			List<GenericTechDataMandatory> dataItems = sourceEntity.getGenericTechnicalDataManadatory();
			List<GenericTechDataMandatoryRepresentation> dataList = new ArrayList<>();
			for (GenericTechDataMandatory dataItem : dataItems) {
				GenericTechDataMandatoryRepresentation data = new GenericTechDataMandatoryRepresentation();
				genericTechDataMandatoryAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setGenericTechnicalData(targetDto);

				dataList.add(data);

			}
			targetDto.setGenericTechnicalDataManadatory(dataList);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(GenericTechnicalData targetEntity, GenericTechnicalDataRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setIsDeleted(sourceDto.getIsDeleted());
		targetEntity.setDataType(sourceDto.getDataType());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setDefaultValue(sourceDto.getDefaultValue());
		Unit unit = unitFactory.createUnit();
		unitAssembler.mergeAggregateWithDto(unit, sourceDto.getUnit());
		targetEntity.setUnit(unit);
		if (sourceDto.getGenericTechnicalDataManadatory() != null && !sourceDto.getGenericTechnicalDataManadatory().isEmpty()) {
			List<GenericTechDataMandatoryRepresentation> dataItems = sourceDto.getGenericTechnicalDataManadatory();
			List<GenericTechDataMandatory> dataList = new ArrayList<>();
			for (GenericTechDataMandatoryRepresentation dataItem : dataItems) {
				GenericTechDataMandatory data = genericTechDataMandatoryFactory.createGenericTDM();
				genericTechDataMandatoryAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setGenericTechnicalData(targetEntity);
				dataList.add(data);

			}
			targetEntity.setGenericTechnicalDataManadatory(dataList);
		}

	}

}