/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedestdl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.generictd.GenericTechDataFactory;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TechnicalDataDepToValuedAssmbler;
import com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataRepresentation;

/**
 * The Class EsDepToValuedTDLAssembler.
 */
public class EsDepToValuedTDLAssembler extends BaseAssembler<EmissionStdDepTDL, TvvValuedEsDepTDLRepresentation> {

	/** The valued generic tech data assembler. */
	@Inject
	TechnicalDataDepToValuedAssmbler valuedGenericTechDataAssembler;

	/** The technical data factory. */
	@Inject
	GenericTechDataFactory technicalDataFactory;

	/**
	 * {@inheritDoc} This method is used for Copying EmissionStandard Dependent List to TvvVAluedEmissionStandardDep
	 * list
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedEsDepTDLRepresentation targetDto, EmissionStdDepTDL sourceEntity) {
		targetDto.setEntityId(0);
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());
		if (sourceEntity.getGenericTechnicalData() != null && !sourceEntity.getGenericTechnicalData().isEmpty()) {
			List<GenericTechnicalData> dataItems = sourceEntity.getGenericTechnicalData();
			List<ValuedGenericDataRepresentation> dataList = new ArrayList<>();
			for (GenericTechnicalData dataItem : dataItems) {
				ValuedGenericDataRepresentation data = new ValuedGenericDataRepresentation();
				valuedGenericTechDataAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setEmsDepTDL(targetDto);
				dataList.add(data);

			}
			targetDto.setValuedGenericData(dataList);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(EmissionStdDepTDL targetEntity, TvvValuedEsDepTDLRepresentation sourceDto) {
		// DO NOTHING
	}	
}
