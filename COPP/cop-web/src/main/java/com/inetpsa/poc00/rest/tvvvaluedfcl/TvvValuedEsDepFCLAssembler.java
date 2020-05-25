/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedfcl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppFactory;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficentsFactory;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCL;
import com.inetpsa.poc00.emsdepfcl.FactorCoeffAppTypeAssembler;
import com.inetpsa.poc00.emsdepfcl.FactorCoeffApplicationTypeRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;

/**
 * The Class TvvValuedEsDepFCLAssembler.
 */
public class TvvValuedEsDepFCLAssembler extends BaseAssembler<TvvValuedEsDepFCL, TvvValuedEsDepFCLRepresentation> {

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/** The factor coeff app type assembler. */
	@Inject
	private FactorCoeffAppTypeAssembler factorCoeffAppTypeAssembler;

	/** The factor coeff app type factory. */
	@Inject
	private FactorCoeffAppFactory factorCoeffAppTypeFactory;

	/** The tvv valued factor coefficents factory. */
	@Inject
	private TvvValuedFactorCoefficentsFactory tvvValuedFactorCoefficentsFactory;

	/** The tvv valued factor coefficent assembler. */
	@Inject
	private TvvValuedFactorCoefficentAssembler tvvValuedFactorCoefficentAssembler;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(TvvValuedEsDepFCL targetEntity, TvvValuedEsDepFCLRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setVersion(sourceDto.getVersion());

		EmissionStandardRepresentation entity = sourceDto.getEmissionStandard();
		EmissionStandard newObj = emissionStandardFactory.createEmissonStandard();
		emissionStandardAssembler.doMergeAggregateWithDto(newObj, entity);

		targetEntity.setEmissionStandard(newObj);
		if (sourceDto.getFactorOrCoeffiecients() != null && !sourceDto.getFactorOrCoeffiecients().isEmpty()) {
			List<TvvValuedFactorCoefficentsRepresentation> dataItems = sourceDto.getFactorOrCoeffiecients();
			List<TvvValuedFactorCoefficents> dataList = new ArrayList<TvvValuedFactorCoefficents>();
			// long i = 0;
			for (TvvValuedFactorCoefficentsRepresentation dataItem : dataItems) {
				TvvValuedFactorCoefficents data = tvvValuedFactorCoefficentsFactory.createTvvValuedFactorCoefficents();
				tvvValuedFactorCoefficentAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setFcList(targetEntity);
				dataList.add(data);
				// i++;

			}
			targetEntity.setFactorOrCoeffiecients(dataList);

		}
		if (sourceDto.getFcApplicationTypes() != null && !sourceDto.getFcApplicationTypes().isEmpty()) {
			Set<FactorCoeffApplicationTypeRepresentation> dataItems = sourceDto.getFcApplicationTypes();
			Set<FactorCoeffApplicationType> dataList = new HashSet<FactorCoeffApplicationType>();
			for (FactorCoeffApplicationTypeRepresentation dataItem : dataItems) {
				FactorCoeffApplicationType data = factorCoeffAppTypeFactory.createFactorCoefficents();
				factorCoeffAppTypeAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			targetEntity.setFcApplicationType(dataList);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedEsDepFCLRepresentation targetDto, TvvValuedEsDepFCL sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());

		EmissionStandard entity = sourceEntity.getEmissionStandard();
		EmissionStandardRepresentation newObj = new EmissionStandardRepresentation();
		emissionStandardAssembler.doAssembleDtoFromAggregate(newObj, entity);

		targetDto.setEmissionStandard(newObj);
		if (sourceEntity.getFactorOrCoeffiecients() != null && !sourceEntity.getFactorOrCoeffiecients().isEmpty()) {
			List<TvvValuedFactorCoefficents> dataItems = sourceEntity.getFactorOrCoeffiecients();
			List<TvvValuedFactorCoefficentsRepresentation> dataList = new ArrayList<TvvValuedFactorCoefficentsRepresentation>();
			for (TvvValuedFactorCoefficents dataItem : dataItems) {
				TvvValuedFactorCoefficentsRepresentation data = new TvvValuedFactorCoefficentsRepresentation();
				tvvValuedFactorCoefficentAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setFcList(targetDto);
				dataList.add(data);

			}

			targetDto.setFactorOrCoeffiecients(dataList);

		}
		if (sourceEntity.getFcApplicationTypes() != null && !sourceEntity.getFcApplicationTypes().isEmpty()) {
			Set<FactorCoeffApplicationType> dataItems = sourceEntity.getFcApplicationTypes();
			Set<FactorCoeffApplicationTypeRepresentation> dataList = new HashSet<FactorCoeffApplicationTypeRepresentation>();
			for (FactorCoeffApplicationType dataItem : dataItems) {
				FactorCoeffApplicationTypeRepresentation data = new FactorCoeffApplicationTypeRepresentation();
				factorCoeffAppTypeAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setFcApplicationTypes(dataList);
		}

	}
}
