package com.inetpsa.poc00.rest.testnature;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.testnature.TestNature;

/**
 * The Class TestNatureRepresentationAssembler.
 */
public class TestNatureRepresentationAssembler extends BaseAssembler<TestNature, TestNatureRepresentation> {

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(TestNatureRepresentation targetDTO, TestNature sourceEntity) {
        targetDTO.setEntityId(sourceEntity.getEntityId());
        targetDTO.setLabel(sourceEntity.getLabel());
        targetDTO.setType(sourceEntity.getType());
        targetDTO.setHierarchy(sourceEntity.getHierarchy());

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(TestNature targerEntity, TestNatureRepresentation sourceDto) {

        targerEntity.setEntityId(sourceDto.getEntityId());

        targerEntity.setLabel(sourceDto.getLabel());
        targerEntity.setType(sourceDto.getType());

        targerEntity.setHierarchy(sourceDto.getHierarchy());
    }

}