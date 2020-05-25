/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedestcl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionFactory;
import com.inetpsa.poc00.rest.tvvvaluedtcl.ValuedGenericConditionAssembler;
import com.inetpsa.poc00.rest.tvvvaluedtcl.ValuedGenericConditionRepresentation;

/**
 * The Class TvvValuedEsDepTCLAssembler.
 */
public class TvvValuedEsDepTCLAssembler extends BaseAssembler<TvvValuedEsDepTCL, TvvValuedEsDepTCLRepresentation> {

	/** The generic test condition assembler. */
	@Inject
	ValuedGenericConditionAssembler genericTestConditionAssembler;

	/** The test condition factory. */
	@Inject
	TvvValuedTestConditionFactory testConditionFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedEsDepTCLRepresentation targetDto, TvvValuedEsDepTCL sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());
		if (sourceEntity.getGenericTestCondition() != null && !sourceEntity.getGenericTestCondition().isEmpty()) {
			List<TvvValuedTestCondition> dataItems = sourceEntity.getGenericTestCondition();
			List<ValuedGenericConditionRepresentation> dataList = new ArrayList<ValuedGenericConditionRepresentation>();
			for (TvvValuedTestCondition dataItem : dataItems) {
				ValuedGenericConditionRepresentation data = new ValuedGenericConditionRepresentation();
				genericTestConditionAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setEmsDepTCL(targetDto);
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
	public void doMergeAggregateWithDto(TvvValuedEsDepTCL targetEntity, TvvValuedEsDepTCLRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setVersion(sourceDto.getVersion());
		if (sourceDto.getGenericTestCondition() != null && !sourceDto.getGenericTestCondition().isEmpty()) {
			List<ValuedGenericConditionRepresentation> dataItems = sourceDto.getGenericTestCondition();
			List<TvvValuedTestCondition> dataList = new ArrayList<TvvValuedTestCondition>();
			// long i = 0;
			for (ValuedGenericConditionRepresentation dataItem : dataItems) {
				TvvValuedTestCondition data = testConditionFactory.createTvvValuedTestCondition();
				genericTestConditionAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvValuedEsTCL(targetEntity);
				dataList.add(data);
				// i++;

			}
			targetEntity.setGenericTestCondition(dataList);
		}
	}

}
