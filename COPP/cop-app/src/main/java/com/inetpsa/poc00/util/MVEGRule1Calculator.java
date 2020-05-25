/*
 * Creation : Nov 24, 2016
 */
package com.inetpsa.poc00.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetRepository;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.dto.StatisticalRuleDataDto;
import com.inetpsa.poc00.dto.ValueDto;

/**
 * The Class MVEGRule1Calculator.
 */
public class MVEGRule1Calculator implements DecisionCalculator {

	/** The pg result set repository. */

	PollutantGasResultSetRepository pgResultSetRepository;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(MVEGRule1Calculator.class);

	/**
	 * Instantiates a new JAPAN rule1 calculator.
	 */
	MVEGRule1Calculator() {

	}

	/**
	 * Instantiates a new JAPAN rule1 calculator.
	 *
	 * @param pgResultSetRepository the pg result set repository
	 */
	public MVEGRule1Calculator(PollutantGasResultSetRepository pgResultSetRepository) {
		this.pgResultSetRepository = pgResultSetRepository;

	}

	/**
	 * This method calculates Statistical Decision for each Pollutant in given PollutantGasResultSet and returns
	 * modified ResultSet.
	 *
	 * @param currentSample the current sample
	 * @param pgResultSet the pg result set
	 * @return the pollutant gas result set
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

		String globalDecision = null;
		int i = 3;
		while (!ConstantsApp.DECISION_A.equalsIgnoreCase(globalDecision) && !ConstantsApp.DECISION_R.equalsIgnoreCase(globalDecision) && i <= currentSample.getNoOfElements()) {
			Map<String, ArrayList<Double>> differenceValues = getAllDifferenceValues(currentSample, i);
			Map<String, Double> allDnValues = getAllDnValues(differenceValues);
			Map<String, Double> allVnValues = getAllVnValues(differenceValues, allDnValues, i);
			List<String> decisionValues = new ArrayList<>();
			/**
			 * Dn value can be calculated using calculateAverage() method in CalculationUtil and Vn using
			 * calculateVariance result is Dn/Vn
			 */
			for (PollutantGasTestResult result : pgResultSet.getPollutantGasTestResult()) {
				String label = result.getTvvValuedPollGasLimit().getPgLabel().getLabel();
				double dnValue = allDnValues.get(label);
				double vnValue = allVnValues.get(label);
				double statisticalResult = dnValue / vnValue;
				String decision = getDecisionForResult(statisticalResult, currentSample.getNoOfElements());
				result.setStatisticalDecision(decision);
				decisionValues.add(decision);
			}
			globalDecision = getGlobalDecision(decisionValues);
			i = i + 1;

		}

		pgResultSet.getVehicleFile().setStatisticalDecision(globalDecision);
		currentSample.setGlobalDecision(globalDecision);

		if (ConstantsApp.DECISION_A.equalsIgnoreCase(globalDecision) || ConstantsApp.DECISION_R.equalsIgnoreCase(globalDecision) || currentSample.getNoOfElements() == ConstantsApp.MAX_ELEMENTS_FOR_MVEG1)

			currentSample.setClosed(true);

		return pgResultSet;

	}

	/**
	 * Gets the decision for result.
	 *
	 * @param statisticalResult the statistical result
	 * @param sampleCount the integer
	 * @return the decision for result
	 */
	private String getDecisionForResult(double statisticalResult, Integer sampleCount) {
		String decision = null;
		RulesSchemaParser parser = new RulesSchemaParser();
		parser.parseInput();
		List<StatisticalRuleDataDto> rules = RulesSchemaParser.getRuleValues();
		for (StatisticalRuleDataDto rule : rules) {
			if (ConstantsApp.MVEG_1.equalsIgnoreCase(rule.getRuleName())) {
				ValueDto valuedto = rule.getValuesDtoMap().get(sampleCount);

				if (statisticalResult > valuedto.getValueAn())
					decision = ConstantsApp.DECISION_A + sampleCount;
				else if (statisticalResult < valuedto.getValueBn())
					decision = ConstantsApp.DECISION_R + sampleCount;
				else
					decision = ConstantsApp.DECISION_I + sampleCount;
			}
		}
		return decision;

	}

	/**
	 * Gets the all vn values.
	 *
	 * @param resultValue the result value
	 * @param allDnValues the all dn values
	 * @param noOfResultSets the i
	 * @return the all vn values
	 */
	private Map<String, Double> getAllVnValues(Map<String, ArrayList<Double>> resultValue, Map<String, Double> allDnValues, int noOfResultSets) {
		Map<String, Double> vnValues = new HashMap<>();
		for (Entry<String, ArrayList<Double>> resultEntry : resultValue.entrySet()) {
			Double vnValue = CalculationUtil.calculateStandardDeviation(resultEntry.getValue(), allDnValues.get(resultEntry.getKey()), noOfResultSets);
			vnValues.put(resultEntry.getKey(), vnValue);
		}
		return vnValues;
	}

	/**
	 * Gets the all dn values.
	 *
	 * @param resultValue the result value
	 * @return the all dn values
	 */
	private Map<String, Double> getAllDnValues(Map<String, ArrayList<Double>> resultValue) {

		Map<String, Double> dnValues = new HashMap<>();
		for (Entry<String, ArrayList<Double>> pollutant : resultValue.entrySet()) {
			double averageValue = CalculationUtil.calculateAverage(pollutant.getValue());
			dnValues.put(pollutant.getKey(), averageValue);
		}
		return dnValues;
	}

	/**
	 * Gets the all difference values.
	 *
	 * @param currentSample the current sample
	 * @param i the i
	 * @return the all difference values
	 */
	private Map<String, ArrayList<Double>> getAllDifferenceValues(StatisticalSample currentSample, int i) {

		List<PollutantGasResultSet> reultSetList = pgResultSetRepository.getLastIResultSetsForSample(currentSample, i);

		Map<String, ArrayList<Double>> resultValue = new HashMap<>();

		for (PollutantGasResultSet pgResultSet : reultSetList) {
			for (PollutantGasTestResult result : pgResultSet.getPollutantGasTestResult()) {
				String pollutantLabel = result.getTvvValuedPollGasLimit().getPgLabel().getLabel();
				if (!resultValue.containsKey(pollutantLabel)) {

					resultValue.put(pollutantLabel, new ArrayList<Double>());
				}
				resultValue.get(pollutantLabel).add(calculateDifference(result));

			}
		}
		return resultValue;
	}

	/**
	 * Calculate difference.
	 *
	 * @param result the result
	 * @return the double
	 */
	private double calculateDifference(PollutantGasTestResult result) {
		TvvValuedPollutantGasLimit pgLimit = result.getTvvValuedPollGasLimit();
		if (result.getValue() != null) {
			double resultValue = Double.parseDouble(result.getValue());
			return pgLimit.getMaxDValue() - resultValue;
		}
		// TODO decide what should be returned in error cases
		return 0;
	}

	/**
	 * Gets the global decision.
	 *
	 * @param resultValue the result value
	 * @return the global decision
	 */
	private String getGlobalDecision(List<String> resultValue) {
		String decision = null;
		for (String result : resultValue) {
			if (result.indexOf(ConstantsApp.DECISION_R) != -1)
				return ConstantsApp.DECISION_R;
			else if (result.indexOf(ConstantsApp.DECISION_I) != -1)
				decision = ConstantsApp.DECISION_I;
		}
		if (ConstantsApp.DECISION_I.equalsIgnoreCase(decision))
			decision = ConstantsApp.DECISION_I;
		else
			decision = ConstantsApp.DECISION_A;
		return decision;

	}

}
