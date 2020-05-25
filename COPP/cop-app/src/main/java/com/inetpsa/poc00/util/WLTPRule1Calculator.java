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
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersRepository;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class WLTPRule1Calculator.
 */
public class WLTPRule1Calculator implements DecisionCalculator {

	/** The pg result set repository. */
	PollutantGasResultSetRepository pgResultSetRepository;

	/** The parameter repository. */
	StatisticalParametersRepository parameterRepository;

	/** The paramteres. */
	;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(MVEGRule1Calculator.class);

	/**
	 * Instantiates a new WLTP rule1 calculator.
	 */
	WLTPRule1Calculator() {
		// Default Constructor
	}

	/**
	 * Instantiates a new JAPAN rule1 calculator.
	 *
	 * @param parameterRepository the parameter repository
	 * @param pgResultSetRepository the pg result set repository
	 */
	public WLTPRule1Calculator(StatisticalParametersRepository parameterRepository, PollutantGasResultSetRepository pgResultSetRepository) {
		this.parameterRepository = parameterRepository;
		this.pgResultSetRepository = pgResultSetRepository;
	}

	/**
	 * {@inheritDoc}
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
		String globalDecision = null;
		int i = 3;
		List<StatisticalCalculationParameters> paramteres = parameterRepository.getParamtersForRule(ConstantsApp.WLTP_1);
		// If Statistical Calculation Parameters are not set then Calculations cannot take place
		if (paramteres.isEmpty()) {
			pgResultSet.getVehicleFile().setStatisticalDecision(ConstantsApp.ERROR_NO_STATISTICAL_PARAMS);
			return pgResultSet;
		}
		Map<String, Double> uncertainityValues = getParameterValues(pgResultSet, paramteres);
		while (!ConstantsApp.DECISION_A.equalsIgnoreCase(globalDecision) && !ConstantsApp.DECISION_R.equalsIgnoreCase(globalDecision) && i < currentSample.getNoOfElements()) {
			Map<String, ArrayList<Double>> xiValues = getAllXiValues(currentSample, i);
			Map<String, Double> allXtestsValues = getAllXtestValues(xiValues);
			Map<String, Double> allVarValues = getAllVarValues(xiValues, allXtestsValues);
			List<String> decisionValues = new ArrayList<>();

			for (PollutantGasTestResult result : pgResultSet.getPollutantGasTestResult()) {

				String label = result.getTvvValuedPollGasLimit().getPgLabel().getLabel();
				double xiValue = calculateXi(result, pgResultSet.getVehicleFile());

				double varValue = allVarValues.get(label);

				String decision = getDecision(xiValue, result, varValue, uncertainityValues.get(label), currentSample.getNoOfElements());
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
	 * Gets the global decision.
	 *
	 * @param decisionValues the decision values
	 * @return the global decision
	 */
	private String getGlobalDecision(List<String> decisionValues) {

		String decision = null;
		for (String result : decisionValues) {
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

	/**
	 * Gets the decision.
	 *
	 * @param xiValue the xi value
	 * @param result the result
	 * @param varValue the var value
	 * @param uncertainityValue the uncertainty value
	 * @param noOfElements the no of elements
	 * @return the decision
	 */
	private String getDecision(double xiValue, PollutantGasTestResult result, double varValue, double uncertainityValue, int noOfElements) {

		String label = result.getTvvValuedPollGasLimit().getPgLabel().getLabel();
		double limit;
		String decision = null;
		// Limit is 1 for CO2 ,for others it is Maximum Default Value
		if (ConstantsApp.CO2.equalsIgnoreCase(label))

			limit = 1;
		else
			limit = result.getTvvValuedPollGasLimit().getMaxDValue();
		double maxCheckValue = uncertainityValue * limit - varValue / limit;

		if (xiValue < maxCheckValue)
			decision = ConstantsApp.DECISION_A;
		else if (xiValue > ((uncertainityValue * limit - ((noOfElements - 3) / 13)) * varValue / limit))
			decision = ConstantsApp.DECISION_R;
		else if ((uncertainityValue * limit - ((noOfElements - 3) / 13)) * varValue / limit > xiValue)
			decision = ConstantsApp.DECISION_I;
		return decision;
	}

	/**
	 * Gets the parameter values.
	 *
	 * @param pgResultSet the pg result set
	 * @param paramteres
	 * @return the parameter values
	 */
	private Map<String, Double> getParameterValues(PollutantGasResultSet pgResultSet, List<StatisticalCalculationParameters> paramteres) {
		Map<String, Double> uncertainityValues = new HashMap<>();

		for (PollutantGasTestResult result : pgResultSet.getPollutantGasTestResult()) {
			for (StatisticalCalculationParameters paramter : paramteres)
				if (paramter.getPollutantGasLabel().getLabel() == result.getTvvValuedPollGasLimit().getPgLabel().getLabel()) {
					uncertainityValues.put(paramter.getPollutantGasLabel().getLabel(), paramter.getUncertainityFactor());
				}
		}
		return uncertainityValues;
	}

	/**
	 * Gets the all var values.
	 *
	 * @param xiValues the xi values
	 * @param allXtestsValues the all xtests values
	 * @param i the i
	 * @return the all var values
	 */
	private Map<String, Double> getAllVarValues(Map<String, ArrayList<Double>> xiValues, Map<String, Double> allXtestsValues) {
		Map<String, Double> varValues = new HashMap<>();
		for (Entry<String, ArrayList<Double>> valueEntry : xiValues.entrySet()) {
			Double varValue = CalculationUtil.calculateVariance(valueEntry.getValue(), allXtestsValues.get(valueEntry.getKey()));
			varValues.put(valueEntry.getKey(), varValue);
		}
		return varValues;
	}

	/**
	 * Gets the all xtest values.
	 *
	 * @param xiValues the xi values
	 * @return the all xtest values
	 */
	private Map<String, Double> getAllXtestValues(Map<String, ArrayList<Double>> xiValues) {
		Map<String, Double> xTestValues = new HashMap<>();
		for (Entry<String, ArrayList<Double>> valueEntry : xiValues.entrySet()) {
			double averageValue = CalculationUtil.calculateAverage(valueEntry.getValue());
			xTestValues.put(valueEntry.getKey(), averageValue);
		}
		return xTestValues;
	}

	/**
	 * Gets the all xi values.
	 *
	 * @param currentSample the current sample
	 * @param i the i
	 * @return the all xi values
	 */
	private Map<String, ArrayList<Double>> getAllXiValues(StatisticalSample currentSample, int i) {

		List<PollutantGasResultSet> reultSetList = pgResultSetRepository.getLastIResultSetsForSample(currentSample, i);
		Map<String, ArrayList<Double>> resultValue = new HashMap<>();

		for (PollutantGasResultSet pgResultSet : reultSetList) {
			for (PollutantGasTestResult result : pgResultSet.getPollutantGasTestResult()) {
				String pollutantLabel = result.getTvvValuedPollGasLimit().getPgLabel().getLabel();
				if (!resultValue.containsKey(pollutantLabel)) {

					resultValue.put(pollutantLabel, new ArrayList<Double>());
				}
				resultValue.get(pollutantLabel).add(calculateXi(result, pgResultSet.getVehicleFile()));

			}
		}
		return resultValue;
	}

	/**
	 * Calculate xi.
	 *
	 * @param result the result
	 * @param vehicleFile the vehicle file
	 * @return the double
	 */
	private Double calculateXi(PollutantGasTestResult result, VehicleFile vehicleFile) {
		double mco2Value = vehicleFile.getVehicle().getmCO2I();
		double xiValue = 0;
		if (result.getValue() != null) {
			double resultValue = Double.parseDouble(result.getValue());
			xiValue = resultValue / mco2Value;
		}
		return xiValue;

	}

}
