/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.poc00.emsdeptdl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.generictd.GenericTechDataFactory;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataAssembler;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataRepresentation;


/**
 * The Class EmsDepTDLAssembler.
 */
public class EmsDepTDLAssembler extends BaseAssembler<EmissionStdDepTDL, EmsDepTDLRepresentation> {

	/** The generic tech data assembler. */
	@Inject
	private GenericTechnicalDataAssembler genericTechDataAssembler;

	/** The technical data factory. */
	@Inject
	private GenericTechDataFactory technicalDataFactory;

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
	public void doAssembleDtoFromAggregate(EmsDepTDLRepresentation targetDto, EmissionStdDepTDL sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());

		EmissionStandard entity = sourceEntity.getEmissionStandard();

		EmissionStandardRepresentation newObj = new EmissionStandardRepresentation();
		emissionStandardAssembler.doAssembleDtoFromAggregate(newObj, entity);

		targetDto.setEmissionStandard(newObj);
		if (sourceEntity.getGenericTechnicalData() != null && !sourceEntity.getGenericTechnicalData().isEmpty()) {
			List<GenericTechnicalData> dataItems = sourceEntity.getGenericTechnicalData();
			List<GenericTechnicalDataRepresentation> dataList = new ArrayList<>();
			for (GenericTechnicalData dataItem : dataItems) {
				GenericTechnicalDataRepresentation data = new GenericTechnicalDataRepresentation();
				genericTechDataAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setEmsDepTDL(targetDto);
				dataList.add(data);

			}

			targetDto.setGenericTechnicalData(dataList);

		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(EmissionStdDepTDL targetEntity, EmsDepTDLRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setVersion(sourceDto.getVersion());

		EmissionStandardRepresentation entity = sourceDto.getEmissionStandard();
		EmissionStandard newObj = emissionStandardFactory.createEmissonStandard();
		emissionStandardAssembler.doMergeAggregateWithDto(newObj, entity);

		targetEntity.setEmissionStandard(newObj);
		if (sourceDto.getGenericTechnicalData() != null && !sourceDto.getGenericTechnicalData().isEmpty()) {
			List<GenericTechnicalDataRepresentation> dataItems = sourceDto.getGenericTechnicalData();
			List<GenericTechnicalData> dataList = new ArrayList<GenericTechnicalData>();
			for (GenericTechnicalDataRepresentation dataItem : dataItems) {
				GenericTechnicalData data = technicalDataFactory.createGenericTechData();
				genericTechDataAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setEmsDepTDL(targetEntity);
				dataList.add(data);

			}
			targetEntity.setGenericTechnicalData(dataList);

		}

	}
}
