/*
 * Creation : Dec 6, 2016
 */
package com.inetpsa.poc00.rest.statisticalrulesparameters;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatsCalcltnRuleRepository;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters;

/**
 * The Class StatisticalParameterAssembler.
 */
public class StatisticalParameterAssembler extends BaseAssembler<StatisticalCalculationParameters, StatisticalParameterRepresentation> {

	/** The pgl repo. */
	@Inject
	private PollutantGasLabelRepository pglRepo;

	/** The stats calcltn rule repo. */
	@Inject
	private StatsCalcltnRuleRepository statsCalcltnRuleRepo;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(StatisticalParameterRepresentation arg0, StatisticalCalculationParameters arg1) {
		// DO NOTHING
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(StatisticalCalculationParameters targetEntity, StatisticalParameterRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setLimit1(sourceDto.getLimit1());
		targetEntity.setLimit2(sourceDto.getLimit2());
		targetEntity.setUncertainityFactor(sourceDto.getUncertainityFactor());
		if (sourceDto.getPglEntityId() != null)
			targetEntity.setPollutantGasLabel(pglRepo.load(sourceDto.getPglEntityId()));

		if (sourceDto.getScrEntityId() != null)
			targetEntity.setStatisticalCalculationRule(statsCalcltnRuleRepo.load(sourceDto.getScrEntityId()));

	}

}
