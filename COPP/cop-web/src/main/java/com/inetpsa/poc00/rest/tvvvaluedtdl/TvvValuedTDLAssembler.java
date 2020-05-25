/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtdl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataFactory;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;

public class TvvValuedTDLAssembler extends BaseAssembler<TvvValuedTvvDepTDL, TvvValuedTvvDepTDLRepresentation> {

	@Inject
	ValuedGenericTechnicalDataAssembler genericTechDataAssembler;
	@Inject
	TvvValuedTechDataFactory technicalDataFactory;

	@Override
	public void doAssembleDtoFromAggregate(TvvValuedTvvDepTDLRepresentation targetDto, TvvValuedTvvDepTDL sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());
		if (sourceEntity.getValuedTechnicalData() != null && !sourceEntity.getValuedTechnicalData().isEmpty()) {
			List<TvvValuedTechData> dataItems = sourceEntity.getValuedTechnicalData();
			List<ValuedGenericDataRepresentation> dataList = new ArrayList<ValuedGenericDataRepresentation>();
			for (TvvValuedTechData dataItem : dataItems) {
				ValuedGenericDataRepresentation data = new ValuedGenericDataRepresentation();
				genericTechDataAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setTvvDepTDL(targetDto);
				dataList.add(data);

			}
			targetDto.setValuedGenericData(dataList);
		}

	}

	@Override
	public void doMergeAggregateWithDto(TvvValuedTvvDepTDL targetEntity, TvvValuedTvvDepTDLRepresentation sourceDto) {

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
				data.setTvvDepTDL(targetEntity);
				dataList.add(data);
				// i++;

			}
			targetEntity.setValuedTechnicalData(dataList);
		}

	}
}
