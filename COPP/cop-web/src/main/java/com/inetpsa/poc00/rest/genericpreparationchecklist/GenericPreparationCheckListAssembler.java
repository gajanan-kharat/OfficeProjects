/*
 * Creation : Oct 19, 2016
 */
package com.inetpsa.poc00.rest.genericpreparationchecklist;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklist;
import com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItem;
import com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItemFactory;
import com.inetpsa.poc00.rest.genericpreparationitem.PreparationItemAssembler;
import com.inetpsa.poc00.rest.genericpreparationitem.PreparationItemRepresentation;

/**
 * The Class GenericPreparationCheckListAssembler.
 */
public class GenericPreparationCheckListAssembler extends BaseAssembler<GenericPreparationChecklist, GenericPreparationCheckListRepresentation> {

	/** The gpi factory. */
	@Inject
	private GenericPreparationItemFactory gpiFactory;

	/** The preparation item assembler. */
	@Inject
	private PreparationItemAssembler preparationItemAssembler;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(GenericPreparationCheckListRepresentation arg0, GenericPreparationChecklist arg1) {
		// Do Nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(GenericPreparationChecklist targetEntity, GenericPreparationCheckListRepresentation sourceDto) {
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setOrder(sourceDto.getOrder());
		targetEntity.setEnabled(sourceDto.getEnabled());

		List<GenericPreparationItem> gpiList = new ArrayList<>();

		for (PreparationItemRepresentation gpiRep : sourceDto.getPreparationItems()) {
			GenericPreparationItem gpiObj = gpiFactory.createGenericPreparationItem();
			gpiObj.setGenericPreparationChecklist(targetEntity);
			preparationItemAssembler.doMergeAggregateWithDto(gpiObj, gpiRep);

			gpiList.add(gpiObj);
		}

		targetEntity.setPreparationItems(gpiList);
	}

}
