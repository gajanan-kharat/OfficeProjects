/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.rest.statisticalcalculationrule;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.statisticalcalculationrule.StatisticalCalculationRule;

public class StatisticalCalculationRuleAssembler extends BaseAssembler<StatisticalCalculationRule, StatisticalCalculationRuleRepresentation> {

	@Override
	public void doAssembleDtoFromAggregate(StatisticalCalculationRuleRepresentation targetDto, StatisticalCalculationRule sourceEntity) {

		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDescription(sourceEntity.getDescription());

	}

	@Override
	public void doMergeAggregateWithDto(StatisticalCalculationRule targetEntity, StatisticalCalculationRuleRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setDescription(sourceDto.getDescription());

	}

}
