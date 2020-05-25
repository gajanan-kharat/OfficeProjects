package com.inetpsa.poc00.rest.codecisionparameters;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersFactory;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.rest.fueltype.FuelTypeAssembler;
import com.inetpsa.poc00.rest.fueltype.FuelTypeFinder;

/**
 * The Class CODecisionParameterAssembler.
 */
public class CODecisionParametersAssembler extends BaseAssembler<CODecisionParameters, CODecisionParametersRepresentation> {

	/**
	 * {@inheritDoc}.
	 *
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Inject
	FuelTypeFinder fuelTypeFinder;

	/** The fuel type assembler. */
	@Inject
	FuelTypeAssembler fuelTypeAssembler;

	/** The fuel typefactory. */
	@Inject
	FuelTypeFactory fuelTypefactory;

	/** The cdp factory. */
	@Inject
	CODecisionParametersFactory cdpFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(CODecisionParametersRepresentation targetDto, CODecisionParameters sourceEntity) {

		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setHigherLimit(sourceEntity.getHigherLimit());
		targetDto.setHigherSymbol(sourceEntity.getHigherSymbol());
		targetDto.setLowerLimit(sourceEntity.getLowerLimit());
		targetDto.setLowerSymbol(sourceEntity.getLowerSymbol());
		FuelType fuelType = sourceEntity.getFuelType();
		String fuelLabel = fuelType.getFuelTypeLable();
		targetDto.setFuelTypeLabel(fuelLabel);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(CODecisionParameters targetEntity, CODecisionParametersRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setHigherLimit(sourceDto.getHigherLimit());
		targetEntity.setHigherSymbol(sourceDto.getHigherSymbol());
		targetEntity.setLowerLimit(sourceDto.getLowerLimit());
		targetEntity.setLowerSymbol(sourceDto.getLowerSymbol());
		FuelType targetFuelType = fuelTypefactory.createFuelType();
		fuelTypeAssembler.doMergeAggregateWithDto(targetFuelType, fuelTypeFinder.findFuelTypeDataByLabel(sourceDto.getFuelTypeLabel()).get(0));
		targetEntity.setFuelType(targetFuelType);

	}

	/**
	 * Assemble from representation.
	 *
	 * @param sourceCODecisionParametersRepresentation the source co decision parameters representation
	 * @return the CO decision parameters
	 */
	public CODecisionParameters assembleFromRepresentation(CODecisionParametersRepresentation sourceCODecisionParametersRepresentation) {

		CODecisionParameters targetCODecisionParameters = cdpFactory.createCODecisionParameters();
		doMergeAggregateWithDto(targetCODecisionParameters, sourceCODecisionParametersRepresentation);
		return targetCODecisionParameters;

	}

	/**
	 * Do assemble dto from aggregate.
	 *
	 * @param sourceList the source list
	 * @return the list
	 */
	public List<CODecisionParametersRepresentation> doAssembleDtoFromAggregate(List<CODecisionParameters> sourceList) {
		List<CODecisionParametersRepresentation> targetList = new ArrayList<CODecisionParametersRepresentation>();

		for (CODecisionParameters odp : sourceList) {
			targetList.add(assembleDtoFromAggregate(odp));
		}
		return targetList;

	}

}
