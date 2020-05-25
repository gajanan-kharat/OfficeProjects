/*
 * Creation : Apr 1, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionFactory;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;

/**
 * The Class TvvDepTCLAssembler.
 */
public class TvvDepTCLAssembler extends BaseAssembler<TvvDepTCL, TvvDepTCLRepresentation> {

	/** The generic test condition assembler. */
	@Inject
	GenericTestConditionAssembler genericTestConditionAssembler;

	/** The test condition factory. */
	@Inject
	GenericTestConditionFactory testConditionFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(TvvDepTCLRepresentation targetDto, TvvDepTCL sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());
		if (sourceEntity.getGenericTestCondition() != null && !sourceEntity.getGenericTestCondition().isEmpty()) {
			List<GenericTestCondition> dataItems = sourceEntity.getGenericTestCondition();
			List<GenericTestConditionRepresentation> dataList = new ArrayList<GenericTestConditionRepresentation>();
			for (GenericTestCondition dataItem : dataItems) {
				GenericTestConditionRepresentation data = new GenericTestConditionRepresentation();
				genericTestConditionAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setTvvDepTCL(targetDto);
				dataList.add(data);

			}
			targetDto.setGenericTestCondition(dataList);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(TvvDepTCL targetEntity, TvvDepTCLRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setVersion(sourceDto.getVersion());
		if (sourceDto.getGenericTestCondition() != null && !sourceDto.getGenericTestCondition().isEmpty()) {
			List<GenericTestConditionRepresentation> dataItems = sourceDto.getGenericTestCondition();
			List<GenericTestCondition> dataList = new ArrayList<GenericTestCondition>();
			// long i = 0;
			for (GenericTestConditionRepresentation dataItem : dataItems) {
				GenericTestCondition data = testConditionFactory.createGenericTestCondtn();
				genericTestConditionAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvDepTCL(targetEntity);
				dataList.add(data);
				// i++;

			}
			targetEntity.setGenericTestCondition(dataList);
		}

	}
}
