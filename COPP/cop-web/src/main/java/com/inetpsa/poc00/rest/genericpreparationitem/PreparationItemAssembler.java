/*
 * Creation : Oct 19, 2016
 */
package com.inetpsa.poc00.rest.genericpreparationitem;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItem;

/**
 * The Class PreparationItemAssembler.
 */
public class PreparationItemAssembler extends BaseAssembler<GenericPreparationItem, PreparationItemRepresentation> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(PreparationItemRepresentation arg0, GenericPreparationItem arg1) {
		// DO NOTHING
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(GenericPreparationItem targetEntity, PreparationItemRepresentation sourceDto) {

		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setHelpText(sourceDto.getHelpText());
		targetEntity.setDataType(sourceDto.getDataType());
		targetEntity.setOrder(sourceDto.getOrder());
		targetEntity.setUnit(sourceDto.getUnit());
		targetEntity.setAuthorizedComment(sourceDto.getAuthorizedComment());
		targetEntity.setMandatory(sourceDto.getMandatory());

	}

}
