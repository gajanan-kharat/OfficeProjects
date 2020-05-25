package com.inetpsa.poc00.rest.gearbox;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.gearbox.GearBox;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;

/**
 * The Class GearBoxAssembler.
 */
public class GearBoxAssembler extends BaseAssembler<GearBox, GearBoxRepresentation> {

    @Inject
    GenomeTVVRuleRepository tvvRuleRepo;

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(GearBoxRepresentation targetDto, GearBox sourceEntity) {
        targetDto.setEntityId(sourceEntity.getEntityId());
        targetDto.setGearBoxLabel(sourceEntity.getLabel());
        targetDto.setGearBoxType(sourceEntity.getType());
        targetDto.setGearboxEntity(sourceEntity.getEntityId());
        targetDto.setDisplayLabel(sourceEntity.getLabel());

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(GearBox targetEntity, GearBoxRepresentation sourceDto) {
        targetEntity.setLabel(sourceDto.getGearBoxLabel());
        targetEntity.setType(sourceDto.getGearBoxType());
        if (sourceDto.getEntityId() != null)
            targetEntity.setEntityId(sourceDto.getEntityId());
        else
            targetEntity.setEntityId(sourceDto.getGearboxEntity());

        if (sourceDto.getB0gTVVRuleEntity() != null)
            targetEntity.setGenomeTvvRuleB0G(tvvRuleRepo.load(sourceDto.getB0gTVVRuleEntity()));
        if (sourceDto.getDbmTVVRuleId() != null)
            targetEntity.setGenomeTvvRuleDBM(tvvRuleRepo.load(sourceDto.getDbmTVVRuleId()));

    }

}
