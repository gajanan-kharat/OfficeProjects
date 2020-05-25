package com.inetpsa.poc00.rest.tirebrand;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.tirebrand.TireBrand;

/**
 * The Class TireBrandAssembler.
 */
public class TireBrandAssembler extends BaseAssembler<TireBrand, TireBrandRepresentation> {

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    protected void doAssembleDtoFromAggregate(TireBrandRepresentation targetDto, TireBrand sourceEntity) {

        targetDto.setEntityId(sourceEntity.getEntityId());
        targetDto.setLabel(sourceEntity.getLabel());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    protected void doMergeAggregateWithDto(TireBrand targetEntity, TireBrandRepresentation sourceDto) {
        targetEntity.setEntityId(sourceDto.getEntityId());
        targetEntity.setLabel(sourceDto.getLabel());

    }

}
