/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.poc00.emsdeptcl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionFactory;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionAssembler;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionRepresentation;


/**
 * The Class EmsDepTCLAssembler.
 */
public class EmsDepTCLAssembler extends BaseAssembler<EmissionStdDepTCL, EmsDepTCLRepresentation> {

	/** The generic test condition assembler. */
	@Inject
	private GenericTestConditionAssembler genericTestConditionAssembler;

	/** The test condition factory. */
	@Inject
	private GenericTestConditionFactory testConditionFactory;

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(EmsDepTCLRepresentation targetDto, EmissionStdDepTCL sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());
		EmissionStandard entity = sourceEntity.getEmissionStandard();
		EmissionStandardRepresentation newObj = new EmissionStandardRepresentation();
		emissionStandardAssembler.doAssembleDtoFromAggregate(newObj, entity);

		targetDto.setEmissionStandard(newObj);
		if (sourceEntity.getGenericTestCondition() != null && !sourceEntity.getGenericTestCondition().isEmpty()) {
			List<GenericTestCondition> dataItems = sourceEntity.getGenericTestCondition();
			List<GenericTestConditionRepresentation> dataList = new ArrayList<>();
			for (GenericTestCondition dataItem : dataItems) {
				GenericTestConditionRepresentation data = new GenericTestConditionRepresentation();
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
	protected void doMergeAggregateWithDto(EmissionStdDepTCL targetEntity, EmsDepTCLRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setVersion(sourceDto.getVersion());
		EmissionStandardRepresentation entity = sourceDto.getEmissionStandard();
		EmissionStandard newObj = emissionStandardFactory.createEmissonStandard();

		emissionStandardAssembler.doMergeAggregateWithDto(newObj, entity);
		targetEntity.setEmissionStandard(newObj);

		if (sourceDto.getGenericTestCondition() != null && !sourceDto.getGenericTestCondition().isEmpty()) {
			List<GenericTestConditionRepresentation> dataItems = sourceDto.getGenericTestCondition();
			List<GenericTestCondition> dataList = new ArrayList<>();
			for (GenericTestConditionRepresentation dataItem : dataItems) {
				GenericTestCondition data = testConditionFactory.createGenericTestCondtn();
				genericTestConditionAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setEmsDepTCL(targetEntity);
				dataList.add(data);

			}
			targetEntity.setGenericTestCondition(dataList);
		}

	}
}
