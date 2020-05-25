/*
 * Creation : Nov 24, 2016
 */
package com.inetpsa.poc00.util;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersRepository;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository;

/**
 * The Class JAPANRule1Calculator.
 */
public class JAPANRule1Calculator implements DecisionCalculator {

	/** The parameter repository. */
	// @Inject
	StatisticalParametersRepository parameterRepository;

	/** The sample repository. */
	StatisticalSampleRepository sampleRepository;

	/**
	 * Instantiates a new JAPAN rule1 calculator.
	 */
	JAPANRule1Calculator() {

	}

	/**
	 * Instantiates a new JAPAN rule1 calculator.
	 *
	 * @param parameterRepository the parameter repository
	 * @param statisticalSampleRepository the statistical sample repository
	 */
	public JAPANRule1Calculator(StatisticalParametersRepository parameterRepository, StatisticalSampleRepository statisticalSampleRepository) {
		this.parameterRepository = parameterRepository;
		this.sampleRepository = statisticalSampleRepository;
	}

	/**
	 * {@inheritDoc} This method calculates Statistical Decision for each Pollutant in given PollutantGasResultSet and
	 * returns modified ResultSet
	 * 
	 * @see com.inetpsa.poc00.util.DecisionCalculator#calculateDecision(com.inetpsa.poc00.domain.statisticalsample.StatisticalSample,
	 *      com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet)
	 */
	@Override
	public PollutantGasResultSet calculateDecision(StatisticalSample currentSample, PollutantGasResultSet pgResultSet) {
		if (currentSample.getNoOfElements() < 3) {
			for (PollutantGasTestResult result : pgResultSet.getPollutantGasTestResult()) {
				result.setStatisticalDecision(ConstantsApp.DECISION_MD);

			}
			pgResultSet.getVehicleFile().setStatisticalDecision(ConstantsApp.DECISION_MD);
			currentSample.setGlobalDecision(ConstantsApp.DECISION_MD);
			return pgResultSet;
		}
		return doCalculations(currentSample, pgResultSet);

	}

	/**
	 * Do calculations.
	 *
	 * @param currentSample the current sample
	 * @param pgResultSet the pg result set
	 * @return the pollutant gas result set
	 */
	private PollutantGasResultSet doCalculations(StatisticalSample currentSample, PollutantGasResultSet pgResultSet) {
		List<String> resultValue = new ArrayList<>();
		List<StatisticalCalculationParameters> paramteres = parameterRepository.getParamtersForRule(ConstantsApp.JAPAN_1);
		// If Statistical Parameters are not Set for this rule then Calculations Cannot Take Place
		if (paramteres.isEmpty()) {
			pgResultSet.getVehicleFile().setStatisticalDecision(ConstantsApp.ERROR_NO_STATISTICAL_PARAMS);
			return pgResultSet;
		}
		// Else Proceed for calculations
		for (PollutantGasTestResult result : pgResultSet.getPollutantGasTestResult()) {
			for (StatisticalCalculationParameters paramter : paramteres)
				if (paramter.getPollutantGasLabel().getLabel() == result.getTvvValuedPollGasLimit().getPgLabel().getLabel()) {
					resultValue.add(getDecision(paramter, result, currentSample));
				}
		}
		pgResultSet.getVehicleFile().setStatisticalDecision(getGlobalDecision(resultValue));
		return pgResultSet;
	}

	/**
	 * This method gets the global decision for VehicleFile based on result values of all Pollutant Results.
	 *
	 * @param resultValue the result value
	 * @return the global decision
	 */
	private String getGlobalDecision(List<String> resultValue) {
		if (resultValue.contains(ConstantsApp.DECISION_R))
			return ConstantsApp.DECISION_R;
		return ConstantsApp.DECISION_A;

	}

	/**
	 * This method returns Statistical Decision given PollutantGasTestResult.
	 *
	 * @param paramter the parameter
	 * @param pollutantGasTestResult the pollutant gas test result
	 * @param currentSample the current sample
	 * @return the decision
	 */
	private String getDecision(StatisticalCalculationParameters paramter, PollutantGasTestResult pollutantGasTestResult, StatisticalSample currentSample) {
		double resultValue;
		if (pollutantGasTestResult.getValue() != null && pollutantGasTestResult.getValue().length() > 0)
			resultValue = Double.parseDouble(pollutantGasTestResult.getValue());
		else
			resultValue = 0.0;
		String statisticalDecision;
		if (resultValue < paramter.getLimit1()) {
			statisticalDecision = ConstantsApp.DECISION_A;
		} else if (resultValue > paramter.getLimit2()) {
			statisticalDecision = ConstantsApp.DECISION_R;
		}

		else {
			double average = sampleRepository.getResultAverage(currentSample, pollutantGasTestResult.getTvvValuedPollGasLimit());
			if (average < paramter.getLimit1()) {

				statisticalDecision = ConstantsApp.DECISION_A;
			} else {
				statisticalDecision = ConstantsApp.DECISION_R;
			}
		}
		pollutantGasTestResult.setStatisticalDecision(statisticalDecision);
		return statisticalDecision;
	}

}
