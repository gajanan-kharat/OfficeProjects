/*
 * Creation : Feb 29, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictd.GenericTechDataFactory;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;

/**
 * The Class TvvDepTDLAssembler.
 */
public class TvvDepTDLAssembler extends BaseAssembler<TvvDepTDL, TvvDepTDLRepresentation> {

	/** The generic tech data assembler. */
	@Inject
	GenericTechnicalDataAssembler genericTechDataAssembler;

	/** The technical data factory. */
	@Inject
	GenericTechDataFactory technicalDataFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(TvvDepTDLRepresentation targetDto, TvvDepTDL sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());
		if (sourceEntity.getGenericTechnicalData() != null && !sourceEntity.getGenericTechnicalData().isEmpty()) {
			List<GenericTechnicalData> dataItems = sourceEntity.getGenericTechnicalData();
			List<GenericTechnicalDataRepresentation> dataList = new ArrayList<GenericTechnicalDataRepresentation>();
			for (GenericTechnicalData dataItem : dataItems) {
				GenericTechnicalDataRepresentation data = new GenericTechnicalDataRepresentation();
				genericTechDataAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setTvvDepTDL(targetDto);
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
	protected void doMergeAggregateWithDto(TvvDepTDL targetEntity, TvvDepTDLRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setVersion(sourceDto.getVersion());
		if (sourceDto.getGenericTechnicalData() != null && !sourceDto.getGenericTechnicalData().isEmpty()) {
			List<GenericTechnicalDataRepresentation> dataItems = sourceDto.getGenericTechnicalData();
			List<GenericTechnicalData> dataList = new ArrayList<GenericTechnicalData>();
			// long i = 0;
			for (GenericTechnicalDataRepresentation dataItem : dataItems) {
				if (!"TRUE".equalsIgnoreCase(dataItem.getIsDeleted())) {
					GenericTechnicalData data = technicalDataFactory.createGenericTechData();
					genericTechDataAssembler.doMergeAggregateWithDto(data, dataItem);
					data.setTvvDepTDL(targetEntity);
					dataList.add(data);
				}
				// i++;

			}
			targetEntity.setGenericTechnicalData(dataList);
		}

	}
}
