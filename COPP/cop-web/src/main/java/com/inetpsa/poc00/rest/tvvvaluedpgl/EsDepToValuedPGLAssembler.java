/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedpgl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;

/**
 * The Class EsDepToValuedPGLAssembler.
 */
public class EsDepToValuedPGLAssembler extends BaseAssembler<PollutantGasLimitList, TvvValuedEsDepPGLRepresentation> {

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The pollutant gas limit assembler. */
	@Inject
	private PollutantGasLimitDepToValuedAssembler pollutantGasLimitAssembler;

	/**
	 * {@inheritDoc} This method is used to copy Pollutant or Gas Limit list to TvvValued Pollutant or GAS limit list
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedEsDepPGLRepresentation pollutantGasLimitListRepresentation, PollutantGasLimitList pollutantGasLimitList) {
		pollutantGasLimitListRepresentation.setEntityId(0);
		pollutantGasLimitListRepresentation.setDescription(pollutantGasLimitList.getDescription());
		pollutantGasLimitListRepresentation.setLabel(pollutantGasLimitList.getLabel());
		pollutantGasLimitListRepresentation.setVersion(pollutantGasLimitList.getVersion());

		EmissionStandard entity = pollutantGasLimitList.getEmissionStandard();
		EmissionStandardRepresentation newObj = new EmissionStandardRepresentation();
		emissionStandardAssembler.doAssembleDtoFromAggregate(newObj, entity);
		// EmissionStandardRepresentation newObj = emissionStandardAssembler.assembleDtoFromAggregate(entity);

		pollutantGasLimitListRepresentation.setEmissionStandard(newObj);
		if (pollutantGasLimitList.getPollutantGasLimit() != null && !pollutantGasLimitList.getPollutantGasLimit().isEmpty()) {
			List<PollutantGasLimit> dataItems = pollutantGasLimitList.getPollutantGasLimit();
			List<TvvValuedPollutantLimitRepresentation> dataList = new ArrayList<TvvValuedPollutantLimitRepresentation>();
			for (PollutantGasLimit dataItem : dataItems) {
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
	public void doMergeAggregateWithDto(PollutantGasLimitList pollutantGasList, TvvValuedEsDepPGLRepresentation pollutantGasListRepresentation) {
		// DO NOTHING
	}
}
