package com.inetpsa.poc00.rest.carfactory;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.factory.CarFactory;

/**
 * The Class CarFactoryAssembler.
 */
public class CarFactoryAssembler extends BaseAssembler<CarFactory, CarFactoryRepresentation> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(CarFactoryRepresentation targetDto, CarFactory sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setCarFactoryLabel(sourceEntity.getLabel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(CarFactory targetEntity, CarFactoryRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setLabel(sourceDto.getCarFactoryLabel());

	}

}
