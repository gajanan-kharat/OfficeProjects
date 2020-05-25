package com.inetpsa.poc00.rest.finalreduction;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;

/**
 * The Class FinalReductionAssembler.
 */
public class FinalReductionAssembler extends BaseAssembler<FinalReductionRatio, FinalReductionRepresentation> {

	@Inject
	private GenomeTVVRuleRepository tvvRuleRepo;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(FinalReductionRepresentation targetDto, FinalReductionRatio sourceEntity) {

		targetDto.setFinalReductionlabel(sourceEntity.getLabel());
		if (sourceEntity.getGenomeTvvRule() != null)
			targetDto.setRuleId(sourceEntity.getGenomeTvvRule().getEntityId());
		targetDto.setTvvRuleId(sourceEntity.getGenomeTvvRule());
		targetDto.setEntityId(sourceEntity.getEntityId());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(FinalReductionRatio targetEntity, FinalReductionRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setLabel(sourceDto.getFinalReductionlabel());
		if (sourceDto.getFinalReductionTvvRuleId() != null)
			targetEntity.setGenomeTvvRule(tvvRuleRepo.load(sourceDto.getFinalReductionTvvRuleId()));

	}

}
