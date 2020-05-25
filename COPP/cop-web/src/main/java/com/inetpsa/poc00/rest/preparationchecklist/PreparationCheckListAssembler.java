package com.inetpsa.poc00.rest.preparationchecklist;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList;
import com.inetpsa.poc00.domain.preparationresult.PreparationResult;
import com.inetpsa.poc00.domain.preparationresult.PreparationResultFactory;
import com.inetpsa.poc00.rest.preparationresult.PreparationResultAssembler;
import com.inetpsa.poc00.rest.preparationresult.PreparationResultRepresentation;

/**
 * The Class PreparationCheckListAssembler.
 */
public class PreparationCheckListAssembler extends BaseAssembler<PreparationCheckList, PreparationCheckListRepresentation> {

	/** The prep result assembler. */
	@Inject
	PreparationResultAssembler prepResultAssembler;

	/** The prep result factory. */
	@Inject
	PreparationResultFactory prepResultFactory;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(PreparationCheckListRepresentation targetDto, PreparationCheckList sourceAggregate) {
		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setLabel(sourceAggregate.getLabel());
		targetDto.setOrder(sourceAggregate.getOrder());
		targetDto.setDescription(sourceAggregate.getDescription());
		targetDto.setEnabled(sourceAggregate.getEnabled());
		targetDto.setTypeOfList(sourceAggregate.getTypeOfList());

		List<PreparationResultRepresentation> prepResultRepresentationList = new ArrayList<>();
		List<PreparationResult> preparationResultList = sourceAggregate.getPreparationResultList();

		if (preparationResultList != null && !preparationResultList.isEmpty()) {

			for (PreparationResult pcl : preparationResultList) {
				PreparationResultRepresentation prRepresentation = new PreparationResultRepresentation();
				prepResultAssembler.doAssembleDtoFromAggregate(prRepresentation, pcl);
				prepResultRepresentationList.add(prRepresentation);
			}

		}

		targetDto.setPreparationResultList(prepResultRepresentationList);

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(PreparationCheckList targetAggregate, PreparationCheckListRepresentation sourceDto) {
		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setLabel(sourceDto.getLabel());
		targetAggregate.setOrder(sourceDto.getOrder());
		targetAggregate.setDescription(sourceDto.getDescription());
		targetAggregate.setEnabled(sourceDto.getEnabled());
		targetAggregate.setTypeOfList(sourceDto.getTypeOfList());

		List<PreparationResult> prepResult = new ArrayList<>();
		List<PreparationResultRepresentation> prepResultRepresentationList = sourceDto.getPreparationResultList();

		if (prepResultRepresentationList != null && !prepResultRepresentationList.isEmpty()) {

			for (PreparationResultRepresentation prr : prepResultRepresentationList) {
				PreparationResult preparationResult = prepResultFactory.createPreparationResult();
				preparationResult.setPreparationCheckList(targetAggregate);
				prepResultAssembler.doMergeAggregateWithDto(preparationResult, prr);
				prepResult.add(preparationResult);
			}
		}

		targetAggregate.setPreparationResultList(prepResult);

	}

}
