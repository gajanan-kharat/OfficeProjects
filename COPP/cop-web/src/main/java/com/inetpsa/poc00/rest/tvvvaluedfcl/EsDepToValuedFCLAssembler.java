/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedfcl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;

/**
 * The Class EsDepToValuedFCLAssembler.
 */
public class EsDepToValuedFCLAssembler extends BaseAssembler<FactorCoefficentList, TvvValuedEsDepFCLRepresentation> {

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The factor coefficent dep to valued assmbler. */
	@Inject
	private FactorCoefficentDepToValuedAssmbler factorCoefficentDepToValuedAssmbler;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(FactorCoefficentList targetEntity, TvvValuedEsDepFCLRepresentation sourceDto) {
		// DO NOTHING
	}

	/**
	 * {@inheritDoc}This method is used to copy FactorCoefficentList to TvvValuedFactor Coefficent List while creating
	 * TVV
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedEsDepFCLRepresentation targetDto, FactorCoefficentList sourceEntity) {
		targetDto.setEntityId(0);
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());

		EmissionStandard entity = sourceEntity.getEmissionStandard();
		EmissionStandardRepresentation newObj = new EmissionStandardRepresentation();
		emissionStandardAssembler.doAssembleDtoFromAggregate(newObj, entity);

		targetDto.setEmissionStandard(newObj);
		if (sourceEntity.getFactorOrCoeffiecients() != null && !sourceEntity.getFactorOrCoeffiecients().isEmpty()) {
			List<FactorCoefficents> dataItems = sourceEntity.getFactorOrCoeffiecients();
			List<TvvValuedFactorCoefficentsRepresentation> dataList = new ArrayList<>();
			for (FactorCoefficents dataItem : dataItems) {
				TvvValuedFactorCoefficentsRepresentation data = new TvvValuedFactorCoefficentsRepresentation();
				factorCoefficentDepToValuedAssmbler.doAssembleDtoFromAggregate(data, dataItem);
				data.setFcList(targetDto);
				dataList.add(data);

			}

			targetDto.setFactorOrCoeffiecients(dataList);

		}

	}
}
