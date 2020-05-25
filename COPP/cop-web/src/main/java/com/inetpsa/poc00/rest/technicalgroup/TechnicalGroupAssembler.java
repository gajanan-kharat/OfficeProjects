package com.inetpsa.poc00.rest.technicalgroup;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupFactory;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupAssembler;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentationAssembler;

/**
 * The Class TechnicalGroupAssembler.
 */
public class TechnicalGroupAssembler extends BaseAssembler<TechnicalGroup, TechnicalGroupRepresentation> {

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Inject
    StatusRepresentationAssembler statusAssembler;
    @Inject
    StatusFactory statusFactory;
    @Inject
    RegulationGroupAssembler regulationGroupAssemmbler;
    @Inject
    RegulationGroupFactory regulationGroupFactory;

    @Override
    protected void doAssembleDtoFromAggregate(TechnicalGroupRepresentation targetDto, TechnicalGroup sourceEntity) {

        targetDto.setEntityId(sourceEntity.getEntityId());
        targetDto.setLabel(sourceEntity.getLabel());
        targetDto.setDescription(sourceEntity.getDescription());
        targetDto.setVersion(sourceEntity.getVersion());
        targetDto.setCreationDate(sourceEntity.getCreationDate());
        targetDto.setModificationDate(sourceEntity.getModificationDate());
        targetDto.setSamplingLabel(sourceEntity.getSamplingLabel());
        if (sourceEntity.getTechgroupstatus() != null) {

            StatusRepresentation statusRepresentation = new StatusRepresentation();
            statusAssembler.doAssembleDtoFromAggregate(statusRepresentation, sourceEntity.getTechgroupstatus());
            targetDto.setTechgroupstatus(statusRepresentation);
        }

        if (sourceEntity.getRegulationGroup() != null) {
            RegulationGroupRepresentation regulationGroupRepresent = new RegulationGroupRepresentation();
            regulationGroupAssemmbler.doAssembleDtoFromAggregate(regulationGroupRepresent, sourceEntity.getRegulationGroup());
            targetDto.setRegulationGroupRepresent(regulationGroupRepresent);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    protected void doMergeAggregateWithDto(TechnicalGroup targetEntity, TechnicalGroupRepresentation sourceDto) {
        targetEntity.setEntityId(sourceDto.getEntityId());
        targetEntity.setLabel(sourceDto.getLabel());
        targetEntity.setDescription(sourceDto.getDescription());
        targetEntity.setVersion(sourceDto.getVersion());
        targetEntity.setSamplingLabel(sourceDto.getSamplingLabel());
        if (sourceDto.getTechgroupstatus() != null) {
            Status status = statusFactory.createStatus();
            statusAssembler.doMergeAggregateWithDto(status, sourceDto.getTechgroupstatus());
            targetEntity.setTechgroupstatus(status);
        }
        if (sourceDto.getRegulationGroupRepresent() != null) {
            RegulationGroup regulationGroup = regulationGroupFactory.createRegulationGroup();
            regulationGroupAssemmbler.doMergeAggregateWithDto(regulationGroup, sourceDto.getRegulationGroupRepresent());
            targetEntity.setRegulationGroup(regulationGroup);
        }

    }

}
