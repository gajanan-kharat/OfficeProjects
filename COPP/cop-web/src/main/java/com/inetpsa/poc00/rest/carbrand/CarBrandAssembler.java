package com.inetpsa.poc00.rest.carbrand;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;

/**
 * The Class CarBrandAssembler.
 */
public class CarBrandAssembler extends BaseAssembler<CarBrand, CarBrandRepresentation> {

	/** The tvv rule repository. */
	@Inject
	private GenomeTVVRuleRepository tvvRuleRepo;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(CarBrandRepresentation targetDto, CarBrand sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setBrandLable(sourceEntity.getBrandLabel());
		targetDto.setMakerLable(sourceEntity.getMakerLabel());
		if (sourceEntity.getGenomeTvvRule() != null)
			targetDto.setRuleID(sourceEntity.getGenomeTvvRule().getEntityId());
		targetDto.setTvvRuleId(sourceEntity.getGenomeTvvRule());
		targetDto.setDisplayLabel(sourceEntity.getBrandLabel() + "-" + sourceEntity.getMakerLabel());
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(CarBrand targetEntity, CarBrandRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setBrandLabel(sourceDto.getBrandLable());
		targetEntity.setMakerLabel(sourceDto.getMakerLable());
		if (sourceDto.getConstructeurTvvRuleId() != null)
			targetEntity.setGenomeTvvRule(tvvRuleRepo.load(sourceDto.getConstructeurTvvRuleId()));
	}

}
