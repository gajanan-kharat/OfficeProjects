/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.application.statisticalcalculations;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Interface CalculationService.
 */
@Service
public interface CalculationService {

	/**
	 * Calculate decision.
	 *
	 * @param pgResultSet the pg result set
	 * @return the pollutant gas result set
	 */
	PollutantGasResultSet calculateDecision(PollutantGasResultSet pgResultSet);

	boolean checkSampleCounter(long entityId, VehicleFile vehicleFile, StatisticalSample sample);
}
