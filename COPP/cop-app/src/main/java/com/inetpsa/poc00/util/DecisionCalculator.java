/*
 * Creation : Nov 24, 2016
 */
package com.inetpsa.poc00.util;

import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;

public interface DecisionCalculator {

	public PollutantGasResultSet calculateDecision(StatisticalSample currentSample, PollutantGasResultSet pgResultSet);

}
