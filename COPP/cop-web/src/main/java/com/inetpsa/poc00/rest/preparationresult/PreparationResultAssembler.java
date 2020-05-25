package com.inetpsa.poc00.rest.preparationresult;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.preparationresult.PreparationResult;

/**
 * The Class PreparationResultAssembler.
 */
public class PreparationResultAssembler extends BaseAssembler<PreparationResult, PreparationResultRepresentation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(PreparationResultRepresentation targetDto, PreparationResult sourceAggregate) {
		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setComment(sourceAggregate.getComment());
		targetDto.setAuthorizedComment(sourceAggregate.getAuthorizedComment());
		targetDto.setConformity(sourceAggregate.getConformity());
		targetDto.setDataType(sourceAggregate.getDataType());
		targetDto.setHelpText(sourceAggregate.getHelpText());
		targetDto.setLabel(sourceAggregate.getLabel());
		targetDto.setMandatory(sourceAggregate.getMandatory());
		targetDto.setOrder(sourceAggregate.getOrder());
		targetDto.setUnit(sourceAggregate.getUnit());
		targetDto.setUpdateDate(sourceAggregate.getUpdateDate());
		targetDto.setCreationDate(sourceAggregate.getCreationDate());
		targetDto.setValue(sourceAggregate.getValue());
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(PreparationResult targetAggregate, PreparationResultRepresentation sourceDto) {
		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setComment(sourceDto.getComment());
		targetAggregate.setConformity(sourceDto.getConformity());
		targetAggregate.setDataType(sourceDto.getDataType());
		targetAggregate.setHelpText(sourceDto.getHelpText());
		targetAggregate.setLabel(sourceDto.getLabel());
		targetAggregate.setMandatory(sourceDto.getMandatory());
		targetAggregate.setOrder(sourceDto.getOrder());
		targetAggregate.setUnit(sourceDto.getUnit());
		targetAggregate.setCreationDate(sourceDto.getCreationDate());
		targetAggregate.setValue(sourceDto.getValue());
		targetAggregate.setAuthorizedComment(sourceDto.getAuthorizedComment());

	}

}
