package com.inetpsa.poc00.rest.technicalcase;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.rest.tvv.TvvAssembler;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;

/**
 * The Class TechnicalCaseAssembler.
 */
public class TechnicalCaseAssembler extends BaseAssembler<TechnicalCase, TechnicalCaseRepresentation> {

    @Inject
    TvvAssembler tvvAssembler;

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(TechnicalCaseRepresentation targetDto, TechnicalCase sourceEntity) {
        targetDto.setEntityId(sourceEntity.getEntityId());
        if (sourceEntity.getTvv() != null) {
            TvvRepresentation tvvRepr = new TvvRepresentation();
            tvvAssembler.doAssembleDtoFromAggregate(tvvRepr, sourceEntity.getTvv());
            targetDto.setTvv(tvvRepr);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(TechnicalCase targetEntity, TechnicalCaseRepresentation sourceDto) {

        targetEntity.setEntityId(sourceDto.getEntityId());

    }

}
