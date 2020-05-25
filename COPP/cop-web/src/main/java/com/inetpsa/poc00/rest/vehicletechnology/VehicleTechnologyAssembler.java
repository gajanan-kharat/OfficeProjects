package com.inetpsa.poc00.rest.vehicletechnology;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;

public class VehicleTechnologyAssembler extends BaseAssembler<VehicleTechnology, VehicleTechnologyRepresentation> {

	@Override
	public void doAssembleDtoFromAggregate(VehicleTechnologyRepresentation targetDto, VehicleTechnology sourceAggregate) {

		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setDescription(sourceAggregate.getDescription());
		targetDto.setLabel(sourceAggregate.getLabel());
	}

	@Override
	public void doMergeAggregateWithDto(VehicleTechnology targetAggregate, VehicleTechnologyRepresentation sourceDto) {

		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setLabel(sourceDto.getLabel());
		targetAggregate.setDescription(sourceDto.getDescription());

	}

}
