/*
 * Creation : Nov 28, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.pollutantorgas;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollGasLimitRepository;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimitFactory;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedPollutantLimitAssembler;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedPollutantLimitRepresentation;

/**
 * PollGasTestResultAssembler.
 *
 * @author mehaj
 */
public class PollGasTestResultAssembler extends BaseAssembler<PollutantGasTestResult, PollutantGasTestResultRepresentation> {
	/** The pollutant gas limit assembler. */
	@Inject
	private TvvValuedPollutantLimitAssembler pollutantGasLimitAssembler;

	/** The tvv valued poll gas limit repository. */
	@Inject
	TvvValuedPollGasLimitRepository tvvValuedPollGasLimitRepository;

	/** The tvv valued pollutant gas limit factory. */
	@Inject
	TvvValuedPollutantGasLimitFactory tvvValuedPollutantGasLimitFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(PollutantGasTestResultRepresentation target, PollutantGasTestResult source) {
		if (source.getEntityId() != null) {
			target.setEntityId(source.getEntityId());
		}
		target.setValue(source.getValue());
		target.setStatisticalDecision(source.getStatisticalDecision());
		target.setStatisticalResult(source.getStatisticalResult());
		TvvValuedPollutantLimitRepresentation tvvValuedPollutantLimitRepresentation = new TvvValuedPollutantLimitRepresentation();
		pollutantGasLimitAssembler.doAssembleDtoFromAggregate(tvvValuedPollutantLimitRepresentation, source.getTvvValuedPollGasLimit());
		target.setTvvValuedPollutantLimitRepresentation(tvvValuedPollutantLimitRepresentation);
		TechnicalCase tc = source.getPollutantGasResultSet().getVehicleFile().getVehicle().getTechnicalCase();
		if (source.getTvvValuedPollGasLimit().getPgLabel().getLabel().equals(Constants.PGLABEL_CO2) && tc.getTechnicalGroup() != null && tc.getTechnicalGroup().getRegulationGroup() != null && tc.getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule() != null
				&& tc.getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule().getLabel().equals(Constants.STATISTICAL_CALC_RULE_WLTP1)) {
			target.getTvvValuedPollutantLimitRepresentation().setMaxDValue(source.getPollutantGasResultSet().getVehicleFile().getVehicle().getmCO2I());

		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(PollutantGasTestResult target, PollutantGasTestResultRepresentation source) {
		target.setEntityId(source.getEntityId());
		target.setValue(source.getValue());
		TvvValuedPollutantGasLimit tvvValuedPollutantLimit;
		if (source.getEntityId() != null) {
			tvvValuedPollutantLimit = tvvValuedPollGasLimitRepository.load(source.getTvvValuedPollutantLimitRepresentation().getEntityId());
		} else {
			tvvValuedPollutantLimit = tvvValuedPollutantGasLimitFactory.createTvvValuedPollutantGasLimit();
		}

		pollutantGasLimitAssembler.doMergeAggregateWithDto(tvvValuedPollutantLimit, source.getTvvValuedPollutantLimitRepresentation());
		target.setTvvValuedPollGasLimit(tvvValuedPollutantLimit);
	}

}
