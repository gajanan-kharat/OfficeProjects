package com.inetpsa.pv2.rest.type;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.type.Type;

/**
 * The Class TypeAssembler.
 */
public class TypeAssembler extends BaseAssembler<Type, TypeRepresentation> {

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(TypeRepresentation targetDto, Type sourceEntity) {
        targetDto.setTypeID(sourceEntity.getEntityId());
        targetDto.setTypeLabel(sourceEntity.getTypeLable());

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(Type targetEntity, TypeRepresentation sourceDto) {
        targetEntity.setEntityId(sourceDto.getTypeID());
        targetEntity.setTypeLable(sourceDto.getTypeLabel());

    }

}
