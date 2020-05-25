package com.inetpsa.poc00.rest.lambdadecisionparameters;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersFactory;
import com.inetpsa.poc00.rest.fueltype.FuelTypeAssembler;
import com.inetpsa.poc00.rest.fueltype.FuelTypeFinder;

/**
 * The Class LambdaDecisionParameterAssembler.
 */
public class LambdaDecisionParametersAssembler extends BaseAssembler<LambdaDecisionParameters, LambdaDecisionParametersRepresentation> {

	/** The fuel type finder. */
	@Inject
	FuelTypeFinder fuelTypeFinder;

	/** The fuel type assembler. */
	@Inject
	FuelTypeAssembler fuelTypeAssembler;

	/** The fuel typefactory. */
	@Inject
	FuelTypeFactory fuelTypefactory;

	/** The ldp factory. */
	@Inject
	LambdaDecisionParametersFactory ldpFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(LambdaDecisionParametersRepresentation targetDto, LambdaDecisionParameters sourceEntity) {

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
	protected void doMergeAggregateWithDto(LambdaDecisionParameters targetEntity, LambdaDecisionParametersRepresentation sourceDto) {
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
	 * Do assemble dto from aggregate.
	 *
	 * @param sourceList the source list
	 * @return the list
	 */
	protected List<LambdaDecisionParametersRepresentation> doAssembleDtoFromAggregate(List<LambdaDecisionParameters> sourceList) {
		List<LambdaDecisionParametersRepresentation> targetList = new ArrayList<LambdaDecisionParametersRepresentation>();

		for (LambdaDecisionParameters ldp : sourceList) {
			targetList.add(assembleDtoFromAggregate(ldp));
		}
		return targetList;

	}

	/**
	 * Assemble from representation.
	 *
	 * @param sourceLambdaDecisionParametersRepresentation the source lambda decision parameters representation
	 * @return the lambda decision parameters
	 */
	protected LambdaDecisionParameters assembleFromRepresentation(LambdaDecisionParametersRepresentation sourceLambdaDecisionParametersRepresentation) {
		LambdaDecisionParameters targetLambdaDecisionParameters = ldpFactory.createLambdaDecisionParameters();
		doMergeAggregateWithDto(targetLambdaDecisionParameters, sourceLambdaDecisionParametersRepresentation);
		return targetLambdaDecisionParameters;

	}
}
