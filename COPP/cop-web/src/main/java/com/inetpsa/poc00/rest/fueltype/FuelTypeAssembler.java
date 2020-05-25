package com.inetpsa.poc00.rest.fueltype;

import javax.inject.Inject;

/*
 * Creation : Mar 23, 2016
 */

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;

/**
 * The Class FuelTypeAssembler.
 */
public class FuelTypeAssembler extends BaseAssembler<FuelType, FuelTypeRepresentation> {

	/** The tvv rule repository. */
	@Inject
	private GenomeTVVRuleRepository tvvRuleRepo;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(FuelTypeRepresentation targetDto, FuelType sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setFuelTypeLable(sourceEntity.getFuelTypeLable());
		if (sourceEntity.getGenomeTvvRule() != null)
			targetDto.setRuleId(sourceEntity.getGenomeTvvRule().getEntityId());
		targetDto.setTvvRuleId(sourceEntity.getGenomeTvvRule());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(FuelType targetEntity, FuelTypeRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setFuelTypeLable(sourceDto.getFuelTypeLable());
		if (sourceDto.getCarbuTvvRuleId() != null)
			targetEntity.setGenomeTvvRule(tvvRuleRepo.load(sourceDto.getCarbuTvvRuleId()));

	}

}