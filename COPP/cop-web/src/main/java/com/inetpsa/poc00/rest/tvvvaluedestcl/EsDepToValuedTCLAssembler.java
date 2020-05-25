/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedestcl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionFactory;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TestConditionDepToValuedAssembler;
import com.inetpsa.poc00.rest.tvvvaluedtcl.ValuedGenericConditionRepresentation;

/**
 * The Class EsDepToValuedTCLAssembler.
 */
public class EsDepToValuedTCLAssembler extends BaseAssembler<EmissionStdDepTCL, TvvValuedEsDepTCLRepresentation> {

	/** The generic test condition assembler. */
	@Inject
	TestConditionDepToValuedAssembler genericTestConditionAssembler;

	/** The test condition factory. */
	@Inject
	TvvValuedTestConditionFactory testConditionFactory;

	/**
	 * {@inheritDoc} This method is used to copy Emission Standard Dependent TCL to TvvValued EmissionStandard Dependent
	 * TCL while creating TVV
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedEsDepTCLRepresentation targetDto, EmissionStdDepTCL sourceEntity) {
		targetDto.setEntityId(0);
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());
		if (sourceEntity.getGenericTestCondition() != null && !sourceEntity.getGenericTestCondition().isEmpty()) {
			List<GenericTestCondition> dataItems = sourceEntity.getGenericTestCondition();
			List<ValuedGenericConditionRepresentation> dataList = new ArrayList<ValuedGenericConditionRepresentation>();
			for (GenericTestCondition dataItem : dataItems) {
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
	protected void doMergeAggregateWithDto(EmissionStdDepTCL targetEntity, TvvValuedEsDepTCLRepresentation sourceDto) {
			//DO NOTHING
	}
}