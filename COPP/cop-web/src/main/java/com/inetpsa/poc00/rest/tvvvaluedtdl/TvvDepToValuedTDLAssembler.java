/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtdl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictd.GenericTechDataFactory;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;

/**
 * The Class TvvDepToValuedTDLAssembler.
 */
public class TvvDepToValuedTDLAssembler extends BaseAssembler<TvvDepTDL, TvvValuedTvvDepTDLRepresentation> {

	/** The valued generic tech data assembler. */
	@Inject
	TechnicalDataDepToValuedAssmbler valuedGenericTechDataAssembler;

	/** The technical data factory. */
	@Inject
	GenericTechDataFactory technicalDataFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedTvvDepTDLRepresentation targetDto, TvvDepTDL sourceEntity) {
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
	protected void doMergeAggregateWithDto(TvvDepTDL targetEntity, TvvValuedTvvDepTDLRepresentation sourceDto) {
		// DO NOTHING
	}
}
