/*
 * Creation : Sep 26, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.rest.typeoftest;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;

/**
 * The TypeOfTestReprAssembler Class
 * 
 * @author mehaj
 */
public class TypeOfTestReprAssembler extends BaseAssembler<TypeOfTest, TypeOfTestRepresentation> {

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(TypeOfTestRepresentation targetDTO, TypeOfTest sourceEntity) {
        targetDTO.setTypeOfTestId(sourceEntity.getEntityId());
        targetDTO.setLabel(sourceEntity.getLabel());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(TypeOfTest targetEntity, TypeOfTestRepresentation sourceDto) {
        targetEntity.setEntityId(sourceDto.getTypeOfTestId());
        targetEntity.setLabel(sourceDto.getLabel());
    }

}
