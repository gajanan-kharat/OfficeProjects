package com.inetpsa.poc00.rest.vehiclecoefficent;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoefficents;

/**
 * The Class VehicleCoeffAssembler.
 */
public class VehicleCoeffAssembler extends BaseAssembler<VehicleCoefficents, VehicleCoeffRepresentation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(VehicleCoeffRepresentation targetDto, VehicleCoefficents sourceAggregate) {

		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setLabel(sourceAggregate.getLabel());
		targetDto.setDescription(sourceAggregate.getDescription());
		targetDto.setF0Coeffiecient(sourceAggregate.getF0Coeffiecient());
		targetDto.setF1Coeffiecient(sourceAggregate.getF1Coeffiecient());
		targetDto.setF2Coeffiecient(sourceAggregate.getF2Coeffiecient());
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(VehicleCoefficents targetAggregate, VehicleCoeffRepresentation sourceDto) {

		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setLabel(sourceDto.getLabel());
		targetAggregate.setDescription(sourceDto.getDescription());
		targetAggregate.setF0Coeffiecient(sourceDto.getF0Coeffiecient());
		targetAggregate.setF1Coeffiecient(sourceDto.getF1Coeffiecient());
		targetAggregate.setF2Coeffiecient(sourceDto.getF2Coeffiecient());

	}

}
