/*
 * Creation : Nov 28, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.rest.resultset;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResultFactory;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResultRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.pollutantorgas.PollGasTestResultAssembler;
import com.inetpsa.poc00.pollutantorgas.PollutantGasTestResultRepresentation;

/**
 * PollGasResultSetAssembler.
 *
 * @author mehaj
 */
public class PollGasResultSetAssembler extends BaseAssembler<PollutantGasResultSet, PollGasResultSetRepresentation> {

	/** The poll gas test result assembler. */
	@Inject
	PollGasTestResultAssembler pollGasTestResultAssembler;

	/** The pollutant gas test result repository. */
	@Inject
	PollutantGasTestResultRepository pollutantGasTestResultRepository;

	/** The pollutant gas test result factory. */
	@Inject
	PollutantGasTestResultFactory pollutantGasTestResultFactory;

	/** The vehicle file repository. */
	@Inject
	VehicleFileRepository vehicleFileRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(PollGasResultSetRepresentation target, PollutantGasResultSet source) {
		target.setEntityId(source.getEntityId());
		TechnicalCase tc = source.getVehicleFile().getVehicle().getTechnicalCase();
		if (tc != null && tc.getTechnicalGroup() != null && tc.getTechnicalGroup().getRegulationGroup() != null && tc.getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule() != null) {
			target.setStatisticalcalcRuleLabel(tc.getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule().getLabel());
		}
		String statisticalDecision = "";
		if (source.getVehicleFile().getStatisticalDecision() != null) {
			if (source.getVehicleFile().getStatisticalDecision().contains("R")) {
				statisticalDecision = "Refusé";
			}
			if (source.getVehicleFile().getStatisticalDecision().contains("I")) {
				statisticalDecision = "Incertitude";
			}

			if (source.getVehicleFile().getStatisticalDecision().contains("A")) {
				statisticalDecision = "Accepté ";
			}
			if (source.getVehicleFile().getStatisticalDecision().contains("MD")) {
				statisticalDecision = "Manque de données";
			}
		}
		target.setVehFileStatisticalDecision(statisticalDecision);
		if (source.getUpdateDate() != null) {
			target.setUpdatedate(new SimpleDateFormat("MM/dd/yyyy").format(source.getUpdateDate()));
		}
		if (source.getEntityId() != null) {
			target.setValidated(true);
		} else
			target.setValidated(false);
		if (source.getInQuarantine() != null) {
			target.setInQuarantine(source.getInQuarantine());
		}

		target.setVehiclefileId(source.getVehicleFile().getEntityId());
		target.setObdTest(source.getObdTest());
		target.setBenchCell(source.getBenchCell());
		List<PollutantGasTestResult> pollutantGasTestResultList = source.getPollutantGasTestResult();

		List<PollutantGasTestResultRepresentation> pollutantGasTestResultRepresentationList = new ArrayList<>();
		for (PollutantGasTestResult pollutantGasTestResult : pollutantGasTestResultList) {

			PollutantGasTestResultRepresentation pollutantGasTestResultRepresentation = new PollutantGasTestResultRepresentation();
			pollGasTestResultAssembler.doAssembleDtoFromAggregate(pollutantGasTestResultRepresentation, pollutantGasTestResult);
			pollutantGasTestResultRepresentationList.add(pollutantGasTestResultRepresentation);
		}
		target.setPollutantGasTestResultRepresentationList(pollutantGasTestResultRepresentationList);
		target.setSetOrder(source.getSetOrder());
		if (source.getKeptInStatSample() != null)
			target.setKeptInStatisticalSample(source.getKeptInStatSample());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(PollutantGasResultSet target, PollGasResultSetRepresentation source) {

		List<PollutantGasTestResultRepresentation> pollutantGasTestResultRepList = source.getPollutantGasTestResultRepresentationList();
		List<PollutantGasTestResult> pollutantGasTestResultList = new ArrayList<>();
		for (PollutantGasTestResultRepresentation pollutantGasTestResultRep : pollutantGasTestResultRepList) {
			PollutantGasTestResult pollutantGasTestResult;
			if (pollutantGasTestResultRep.getEntityId() == null) {
				pollutantGasTestResult = pollutantGasTestResultFactory.createPollutantGasTestResult();
				pollutantGasTestResult.setPollutantGasResultSet(target);
			} else {
				pollutantGasTestResult = pollutantGasTestResultRepository.load(pollutantGasTestResultRep.getEntityId());
			}
			pollGasTestResultAssembler.doMergeAggregateWithDto(pollutantGasTestResult, pollutantGasTestResultRep);
			pollutantGasTestResultList.add(pollutantGasTestResult);
		}
		target.setPollutantGasTestResult(pollutantGasTestResultList);
		target.setEntityId(source.getEntityId());
		target.setObdTest(source.getObdTest());
		target.setBenchCell(source.getBenchCell());
		target.setInQuarantine(source.isInQuarantine());
		target.setKeptInStatSample(source.isKeptInStatisticalSample());
		target.setSetOrder(source.getSetOrder());
		target.setVehicleFile(vehicleFileRepository.loadVehicle(source.getVehiclefileId()));
	}

}
