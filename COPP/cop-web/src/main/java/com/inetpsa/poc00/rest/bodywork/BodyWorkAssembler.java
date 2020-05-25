package com.inetpsa.poc00.rest.bodywork;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.bodywork.Bodywork;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;

/**
 * The Class BodyWorkAssembler.
 */
public class BodyWorkAssembler extends BaseAssembler<Bodywork, BodyWorkRepresentation> {

	/** The tvv rule repository. */
	@Inject
	private GenomeTVVRuleRepository tvvRuleRepo;

	/* 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(BodyWorkRepresentation targetDto, Bodywork sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setLable(sourceEntity.getLabel());
		targetDto.setSilhoutteLable(sourceEntity.getLabel());
		if (sourceEntity.getGenomeTvvRule() != null)
			targetDto.setRuleID(sourceEntity.getGenomeTvvRule().getEntityId());
		targetDto.setTvvRuleId(sourceEntity.getGenomeTvvRule());

	}

	/* 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(Bodywork targetEntity, BodyWorkRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setLabel(sourceDto.getSilhoutteLable());
		if (sourceDto.getSilhoutteTvvRuleId() != null)
			targetEntity.setGenomeTvvRule(tvvRuleRepo.load(sourceDto.getSilhoutteTvvRuleId()));

	}

}
