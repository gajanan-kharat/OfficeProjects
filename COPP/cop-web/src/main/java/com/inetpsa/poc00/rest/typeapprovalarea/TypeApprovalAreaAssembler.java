package com.inetpsa.poc00.rest.typeapprovalarea;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;

public class TypeApprovalAreaAssembler extends BaseAssembler<TypeApprovalArea, TypeApprovalAreaRepresentation> {

	@Override
	public void doAssembleDtoFromAggregate(TypeApprovalAreaRepresentation targetDto, TypeApprovalArea sourceAggregate) {

		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setDescription(sourceAggregate.getDescription());
		targetDto.setLabel(sourceAggregate.getLabel());

	}

	@Override
	public void doMergeAggregateWithDto(TypeApprovalArea targetAggregate, TypeApprovalAreaRepresentation sourceDto) {

		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setLabel(sourceDto.getLabel());
		targetAggregate.setDescription(sourceDto.getDescription());

	}

}
