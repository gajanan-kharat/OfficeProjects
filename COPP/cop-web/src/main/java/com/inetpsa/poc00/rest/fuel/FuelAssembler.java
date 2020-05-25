package com.inetpsa.poc00.rest.fuel;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;

/**
 * The Class FuelAssembler.
 */
public class FuelAssembler extends BaseAssembler<Fuel, FuelRepresentation> {

	/** The fuel repo. */
	@Inject
	FuelTypeRepository fuelTypeRepo;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(FuelRepresentation targetDto, Fuel sourceAggregate) {

		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setLabel(sourceAggregate.getLabel());
		targetDto.setDescription(sourceAggregate.getDescription());
		targetDto.setDefaultFuel(sourceAggregate.getDefaultFuel());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(Fuel targetAggregate, FuelRepresentation sourceDto) {

		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setLabel(sourceDto.getLabel());
		targetAggregate.setDescription(sourceDto.getDescription());
		targetAggregate.setDefaultFuel(sourceDto.isDefaultFuel());
		if (sourceDto.getFuelTypeId() != null)
			targetAggregate.setFuelType(fuelTypeRepo.load(sourceDto.getFuelTypeId()));

	}

}
