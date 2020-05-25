/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.domain.statisticalsample;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Interface StatisticalSampleRepository.
 */
public interface StatisticalSampleRepository extends GenericRepository<StatisticalSample, Long> {

	/**
	 * Gets the sample for vehicle file.
	 *
	 * @param technicalCaseId the technical case id
	 * @param factoryId the factory id
	 * @param typeOftestId the type oftest id
	 * @return the sample for vehicle file
	 */
	List<StatisticalSample> getSampleForVehicleFile(long technicalCaseId, long factoryId, long typeOftestId);

	/**
	 * Save statistical sample.
	 * 
	 * @param newSample the new sample
	 * @return the statistical sample
	 */
	StatisticalSample saveStatisticalSample(StatisticalSample newSample);

	/**
	 * Gets the technical case.
	 * 
	 * @param statisticalSampleEntityId the statistical sample entity id
	 * @return the technical case
	 */
	TechnicalCase getTechnicalCase(Long statisticalSampleEntityId);

	/**
	 * Gets the tvv.
	 * 
	 * @param statisticalSampleEntityId the statistical sample entity id
	 * @return the tvv
	 */
	TVV getTVV(Long statisticalSampleEntityId);

	/**
	 * Gets the result average.
	 *
	 * @param currentSample the current sample
	 * @param tvvValuedPollutantGasLimit the tvv valued pollutant gas limit
	 * @return the result average
	 */
	double getResultAverage(StatisticalSample currentSample, TvvValuedPollutantGasLimit tvvValuedPollutantGasLimit);

	/**
	 * Gets the pollutant gas result set.
	 *
	 * @param statisticalSampleEntityId the statistical sample entity id
	 * @return the pollutant gas result set
	 */
	List<PollutantGasResultSet> getPollutantGasResultSet(Long statisticalSampleEntityId);

	/**
	 * Gets the vehicle chassis number for statistical sample.
	 *
	 * @param technicalCaseEntityId the technical case entity id
	 * @return the vehicle chassis number for statistical sample
	 */
	List<String> getVehicleChassisNumber(Long technicalCaseEntityId);

	/**
	 * Gets the pollutant gas test result statistical result for statistical sample.
	 *
	 * @param statisticalSampleEntityId the statistical sample entity id
	 * @return the pollutant gas test result statistical result for statistical sample
	 */
	List<Long> getPollutantGasTestResultStatisticalResult(Long statisticalSampleEntityId);

	/**
	 * Gets the pollutant gas test result statistical decision for statistical sample.
	 *
	 * @param statisticalSampleEntityId the statistical sample entity id
	 * @return the pollutant gas test result statistical decision for statistical sample
	 */
	List<String> getPollutantGasTestResultStatisticalDecision(Long statisticalSampleEntityId);

	/**
	 * Gets the vehicle file result statistical decision for statistical sample.
	 *
	 * @param technicalCaseEntityId the technical case entity id
	 * @return the vehicle file result statistical decision for statistical sample
	 */
	List<String> getVehicleFileResultStatisticalDecision(Long technicalCaseEntityId);

	/**
	 * Gets the pollutant gas limit for vehicle file.
	 *
	 * @param vehicleFileEntityID the vehicle file entity id
	 * @return the pollutant gas limit for vehicle file
	 */
	TvvValuedPollutantGasLimit getPollutantGasLimitForVehicleFile(Long vehicleFileEntityID);

	/**
	 * Gets the pollutant gas test for vehicles.
	 *
	 * @param vehicleEntityID the vehicle entity id
	 * @return the pollutant gas test for vehicles
	 */
	List<PollutantGasTestResult> getPollutantGasTestForVehicles(Long vehicleEntityID);

	/**
	 * Gets the vehicle.
	 *
	 * @param technicalCaseEntityId the technical case entity id
	 * @return the vehicle
	 */
	List<Vehicle> getVehicle(Long technicalCaseEntityId);

	/**
	 * Gets the statistical sample for stat curves.
	 *
	 * @param statisticalSampleEntityId the statistical sample entity id
	 * @return the statistical sample for stat curves
	 */
	StatisticalSample getStatisticalSampleForStatCurves(Long statisticalSampleEntityId);

	/**
	 * Gets the vehicle file.
	 *
	 * @param statisticalSampleEntityId the statistical sample entity id
	 * @return the vehicle file
	 */
	List<VehicleFile> getVehicleFile(Long statisticalSampleEntityId);

	/**
	 * Gets the vehicle based on pg result.
	 *
	 * @param statisticalSampleEntityId the statistical sample entity id
	 * @return the vehicle based on pg result
	 */
	List<Vehicle> getVehicleBasedOnPgResult(Long statisticalSampleEntityId);

	/**
	 * Gets the pg test result.
	 *
	 * @param statisticalSampleEntityId the statistical sample entity id
	 * @return the pg test result
	 */
	List<PollutantGasTestResult> getPgTestResult(Long statisticalSampleEntityId);

	/**
	 * Gets the sampleby sample parameter.
	 *
	 * @param scpEntityId the scp entity id
	 * @return the sampleby sample parameter
	 */
	List<StatisticalSample> getSamplebySampleParameter(Long scpEntityId);

}
