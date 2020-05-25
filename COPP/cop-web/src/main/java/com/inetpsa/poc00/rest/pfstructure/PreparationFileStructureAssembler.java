/*
 * Creation : Oct 14, 2016
 */
package com.inetpsa.poc00.rest.pfstructure;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklist;
import com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklistFactory;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructure;
import com.inetpsa.poc00.rest.genericpreparationchecklist.GenericPreparationCheckListAssembler;
import com.inetpsa.poc00.rest.genericpreparationchecklist.GenericPreparationCheckListRepresentation;

/**
 * The Class PreparationFileStructureAssembler.
 */
public class PreparationFileStructureAssembler extends BaseAssembler<PreparationFileStructure, PreparationFileStructureRepresentation> {

	/** The gpc assembler. */
	@Inject
	private GenericPreparationCheckListAssembler gpcAssembler;

	/** The gpc factory. */
	@Inject
	private GenericPreparationChecklistFactory gpcFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(PreparationFileStructureRepresentation targetDto, PreparationFileStructure sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setVersion(sourceEntity.getVersion());
		targetDto.setModificationDate(sourceEntity.getModificationDate());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(PreparationFileStructure targetEntity, PreparationFileStructureRepresentation sourceDto) {
		Integer version = 1;
		if (sourceDto.getVersion() != null) {
			version += Integer.parseInt(sourceDto.getVersion());
		}
		targetEntity.setModificationDate(Calendar.getInstance().getTime());
		targetEntity.setVersion(version.toString());

		List<GenericPreparationChecklist> gpcList = new ArrayList<>();

		for (GenericPreparationCheckListRepresentation gpcRep : sourceDto.getPreparationChecklists()) {
			GenericPreparationChecklist gpcObj = gpcFactory.createGenericPreparationChecklist();
			gpcObj.setPreparationFileStructure(targetEntity);
			gpcAssembler.doMergeAggregateWithDto(gpcObj, gpcRep);
			gpcList.add(gpcObj);
		}
		targetEntity.setPreparationCheckLists(gpcList);
	}

	/**
	 * Do merge aggregate with dto2.
	 *
	 * @param targetEntity the target entity
	 * @param sourceDto the source dto
	 */
	public void prepareEntityFromDto(PreparationFileStructure targetEntity, PreparationFileStructureRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setModificationDate(sourceDto.getModificationDate());
		targetEntity.setVersion(sourceDto.getVersion());
	}

}
