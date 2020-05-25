/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedestdl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataFactory;
import com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericTechnicalDataAssembler;

/**
 * The Class TvvValuedEsDepTDLAssembler.
 */
public class TvvValuedEsDepTDLAssembler extends BaseAssembler<TvvValuedEsDepTDL, TvvValuedEsDepTDLRepresentation> {

	/** The generic tech data assembler. */
	@Inject
	ValuedGenericTechnicalDataAssembler genericTechDataAssembler;

	/** The technical data factory. */
	@Inject
	TvvValuedTechDataFactory technicalDataFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedEsDepTDLRepresentation targetDto, TvvValuedEsDepTDL sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());
		if (sourceEntity.getGenericTechnicalData() != null && !sourceEntity.getGenericTechnicalData().isEmpty()) {
			List<TvvValuedTechData> dataItems = sourceEntity.getGenericTechnicalData();
			List<ValuedGenericDataRepresentation> dataList = new ArrayList<ValuedGenericDataRepresentation>();
			for (TvvValuedTechData dataItem : dataItems) {
				ValuedGenericDataRepresentation data = new ValuedGenericDataRepresentation();
				genericTechDataAssembler.doAssembleDtoFromAggregate(data, dataItem);
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
	public void doMergeAggregateWithDto(TvvValuedEsDepTDL targetEntity, TvvValuedEsDepTDLRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setVersion(sourceDto.getVersion());
		if (sourceDto.getValuedGenericData() != null && !sourceDto.getValuedGenericData().isEmpty()) {
			List<ValuedGenericDataRepresentation> dataItems = sourceDto.getValuedGenericData();
			List<TvvValuedTechData> dataList = new ArrayList<TvvValuedTechData>();
			// long i = 0;
			for (ValuedGenericDataRepresentation dataItem : dataItems) {
				TvvValuedTechData data = technicalDataFactory.createTvvValuedTechData();
				genericTechDataAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvValuedEsTDL(targetEntity);
				dataList.add(data);
				// i++;

			}
			targetEntity.setGenericTechnicalData(dataList);
		}

	}
}
