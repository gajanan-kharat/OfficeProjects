package com.inetpsa.pv2.rest.ruleofuses;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.ruleofuses.RuleOfUses;

/**
 * The Class RuleOfUsesAssembler.
 */
public class RuleOfUsesAssembler extends BaseAssembler<RuleOfUses, RuleOfUsesRepresentation> {

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(RuleOfUsesRepresentation targetDto, RuleOfUses sourceEntity) {
        targetDto.setId(sourceEntity.getEntityId());
        targetDto.setName(sourceEntity.getName());
        targetDto.setDocLink(sourceEntity.getDocLink());

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(RuleOfUses targetEntity, RuleOfUsesRepresentation sourceDto) {
        targetEntity.setEntityId(sourceDto.getId());
        targetEntity.setName(sourceDto.getName());
        targetEntity.setDocLink(sourceDto.getDocLink());

    }

}
