/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtcl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionFactory;

/**
 * The Class TvvDepToValuedTCLAssemler.
 */
public class TvvDepToValuedTCLAssemler extends BaseAssembler<TvvDepTCL, TvvValuedTvvDepTCLRepresentation> {

	/** The generic test condition assembler. */
	@Inject
	TestConditionDepToValuedAssembler genericTestConditionAssembler;

	/** The test condition factory. */
	@Inject
	TvvValuedTestConditionFactory testConditionFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedTvvDepTCLRepresentation targetDto, TvvDepTCL sourceEntity) {
		targetDto.setEntityId(0);
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(TvvDepTCL targetEntity, TvvValuedTvvDepTCLRepresentation sourceDto) {
		// DO NOTHING
	}
}
