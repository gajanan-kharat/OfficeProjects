/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.rest.statisticalcurves;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.pollutantorgas.PollGasTestResultAssembler;
import com.inetpsa.poc00.pollutantorgas.PollutantGasTestResultRepresentation;
import com.inetpsa.poc00.rest.statisticalsample.StatisticalSampleRepresentation;
import com.inetpsa.poc00.rest.technicalcase.TechnicalCaseAssembler;
import com.inetpsa.poc00.rest.technicalcase.TechnicalCaseRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvAssembler;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedPollutantLimitAssembler;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedPollutantLimitRepresentation;

/**
 * The Class ExportStatisticalCurvesDataAssembler.
 */
public class ExportStatisticalCurvesDataAssembler extends BaseAssembler<StatisticalSample, StatisticalSampleRepresentation> {

	/** The tvv assembler. */
	@Inject
	TvvAssembler tvvAssembler;

	/** The valued pollutant limit assembler. */
	@Inject
	TvvValuedPollutantLimitAssembler valuedPollutantLimitAssembler;

	/** The technical case assembler. */
	@Inject
	TechnicalCaseAssembler technicalCaseAssembler;

	/** The pgtr assembler. */
	@Inject
	PollGasTestResultAssembler pgtrAssembler;

	/**
	 * From tvv valued pollutant gas limit to tvv valued pollutant limit representation.
	 *
	 * @param valuedPollutantLimitsList the valued pollutant limits list
	 * @return the list
	 */
	List<TvvValuedPollutantLimitRepresentation> fromTvvValuedPollutantGasLimitToTvvValuedPollutantLimitRepresentation(List<TvvValuedPollutantGasLimit> valuedPollutantLimitsList) {
		List<TvvValuedPollutantLimitRepresentation> valuedPollutantLimitRepresentationsList = new ArrayList<TvvValuedPollutantLimitRepresentation>();

		for (TvvValuedPollutantGasLimit tvvValuedPollutantLimitRepresentation : valuedPollutantLimitsList) {
			valuedPollutantLimitRepresentationsList.add(valuedPollutantLimitAssembler.assembleDtoFromAggregate(tvvValuedPollutantLimitRepresentation));

		}
		return valuedPollutantLimitRepresentationsList;

	}

	/**
	 * From technical case to technical case representation.
	 *
	 * @param technicalCasesList the technical cases list
	 * @return the list
	 */
	List<TechnicalCaseRepresentation> fromTechnicalCaseToTechnicalCaseRepresentation(List<TechnicalCase> technicalCasesList) {
		List<TechnicalCaseRepresentation> technicalCaseRepresentations = new ArrayList<TechnicalCaseRepresentation>();
		for (TechnicalCase technicalCase : technicalCasesList) {
			technicalCaseRepresentations.add(technicalCaseAssembler.assembleDtoFromAggregate(technicalCase));
		}

		return technicalCaseRepresentations;

	}

	/**
	 * From pg test to pg test representation.
	 *
	 * @param pgtlist the pgtlist
	 * @return the list
	 */
	List<PollutantGasTestResultRepresentation> fromPGTestToPGTestRepresentation(List<PollutantGasTestResult> pgtlist) {
		List<PollutantGasTestResultRepresentation> pgtrRepList = new ArrayList<>();
		for (PollutantGasTestResult pgt : pgtlist) {
			pgtrRepList.add(pgtrAssembler.assembleDtoFromAggregate(pgt));
		}

		return pgtrRepList;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(StatisticalSampleRepresentation arg0, StatisticalSample arg1) {
		// NOT IN USE

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(StatisticalSample arg0, StatisticalSampleRepresentation arg1) {
		// NOT IN USE

	}
}
