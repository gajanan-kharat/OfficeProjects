/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedpgl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimitFactory;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;

/**
 * The Class TvvValuedEsDepPGLAssembler.
 */
public class TvvValuedEsDepPGLAssembler extends BaseAssembler<TvvValuedEsDepPGL, TvvValuedEsDepPGLRepresentation> {

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The pollutant gas limit assembler. */
	@Inject
	private TvvValuedPollutantLimitAssembler pollutantGasLimitAssembler;

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/** The pollutant gas limit factory. */
	@Inject
	private TvvValuedPollutantGasLimitFactory pollutantGasLimitFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedEsDepPGLRepresentation pollutantGasLimitListRepresentation, TvvValuedEsDepPGL pollutantGasLimitList) {
		pollutantGasLimitListRepresentation.setEntityId(pollutantGasLimitList.getEntityId());
		pollutantGasLimitListRepresentation.setDescription(pollutantGasLimitList.getDescription());
		pollutantGasLimitListRepresentation.setLabel(pollutantGasLimitList.getLabel());
		pollutantGasLimitListRepresentation.setVersion(pollutantGasLimitList.getVersion());

		EmissionStandard entity = pollutantGasLimitList.getEmissionStandard();
		EmissionStandardRepresentation newObj = new EmissionStandardRepresentation();
		emissionStandardAssembler.doAssembleDtoFromAggregate(newObj, entity);
		// EmissionStandardRepresentation newObj = emissionStandardAssembler.assembleDtoFromAggregate(entity);

		pollutantGasLimitListRepresentation.setEmissionStandard(newObj);
		if (pollutantGasLimitList.getPollutantGasLimit() != null && !pollutantGasLimitList.getPollutantGasLimit().isEmpty()) {
			List<TvvValuedPollutantGasLimit> dataItems = pollutantGasLimitList.getPollutantGasLimit();
			List<TvvValuedPollutantLimitRepresentation> dataList = new ArrayList<TvvValuedPollutantLimitRepresentation>();
			for (TvvValuedPollutantGasLimit dataItem : dataItems) {
				if (dataItem.getPgLabel().getLabel().equalsIgnoreCase(Constants.LIMIT_CO2)) {
					pollutantGasLimitListRepresentation.setMaxCo2Limit(dataItem.getMaxDValue());
					pollutantGasLimitListRepresentation.setMinCo2Limit(dataItem.getMinDValue());
				}
				TvvValuedPollutantLimitRepresentation data = new TvvValuedPollutantLimitRepresentation();
				pollutantGasLimitAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setPollutantGasLimitList(pollutantGasLimitListRepresentation);
				dataList.add(data);

			}

			pollutantGasLimitListRepresentation.setPollutantGasLimit(dataList);

		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(TvvValuedEsDepPGL pollutantGasList, TvvValuedEsDepPGLRepresentation pollutantGasListRepresentation) {
		pollutantGasList.setEntityId(pollutantGasListRepresentation.getEntityId());
		pollutantGasList.setDescription(pollutantGasListRepresentation.getDescription());
		pollutantGasList.setLabel(pollutantGasListRepresentation.getLabel());
		pollutantGasList.setVersion(pollutantGasListRepresentation.getVersion());

		EmissionStandardRepresentation entity = pollutantGasListRepresentation.getEmissionStandard();
		EmissionStandard newObj = emissionStandardFactory.createEmissonStandard();
		emissionStandardAssembler.doMergeAggregateWithDto(newObj, entity);

		pollutantGasList.setEmissionStandard(newObj);
		if (pollutantGasListRepresentation.getPollutantGasLimit() != null && !pollutantGasListRepresentation.getPollutantGasLimit().isEmpty()) {
			List<TvvValuedPollutantLimitRepresentation> dataItems = pollutantGasListRepresentation.getPollutantGasLimit();
			List<TvvValuedPollutantGasLimit> dataList = new ArrayList<TvvValuedPollutantGasLimit>();
			// long i = 0;
			for (TvvValuedPollutantLimitRepresentation dataItem : dataItems) {
				TvvValuedPollutantGasLimit data = pollutantGasLimitFactory.createTvvValuedPollutantGasLimit();
				pollutantGasLimitAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvValuedEsDepPGL(pollutantGasList);
				dataList.add(data);
				// i++;

			}
			pollutantGasList.setPollutantGasLimit(dataList);

		}
	}
}
