package com.inetpsa.poc00.rest.opacitydecisionparameters;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersFactory;
import com.inetpsa.poc00.rest.fueltype.FuelTypeAssembler;
import com.inetpsa.poc00.rest.fueltype.FuelTypeFinder;

/**
 * The Class OpacityDecisionParameterAssembler.
 */
public class OpacityDecisionParametersAssembler extends BaseAssembler<OpacityDecisionParameters, OpacityDecisionParametersRepresentation> {

	/** The fuel type finder. */
	@Inject
	FuelTypeFinder fuelTypeFinder;

	/** The fuel type assembler. */
	@Inject
	FuelTypeAssembler fuelTypeAssembler;

	/** The fuel typefactory. */
	@Inject
	FuelTypeFactory fuelTypefactory;

	/** The odp factory. */
	@Inject
	OpacityDecisionParametersFactory odpFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(OpacityDecisionParametersRepresentation targetDto, OpacityDecisionParameters sourceEntity) {

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
	protected void doMergeAggregateWithDto(OpacityDecisionParameters targetEntity, OpacityDecisionParametersRepresentation sourceDto) {
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
	 * @param sourceOpacityDecisionParametersRepresentation the source opacity decision parameters representation
	 * @return the opacity decision parameters
	 */
	public OpacityDecisionParameters assembleFromRepresentation(OpacityDecisionParametersRepresentation sourceOpacityDecisionParametersRepresentation) {

		OpacityDecisionParameters targetOpacityDecisionParameters = odpFactory.createOpacityDecisionParameters();
		doMergeAggregateWithDto(targetOpacityDecisionParameters, sourceOpacityDecisionParametersRepresentation);
		return targetOpacityDecisionParameters;

	}

	/**
	 * Do assemble dto from aggregate.
	 *
	 * @param sourceList the source list
	 * @return the list
	 */
	public List<OpacityDecisionParametersRepresentation> doAssembleDtoFromAggregate(List<OpacityDecisionParameters> sourceList) {
		List<OpacityDecisionParametersRepresentation> targetList = new ArrayList<OpacityDecisionParametersRepresentation>();

		for (OpacityDecisionParameters odp : sourceList) {
			targetList.add(assembleDtoFromAggregate(odp));
		}
		return targetList;

	}

}
