package com.inetpsa.poc00.domain.pollutantgasresultset;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;

/**
 * The Interface PollutantGasResultSetRepository.
 */
public interface PollutantGasResultSetRepository extends GenericRepository<PollutantGasResultSet, Long> {

	/**
	 * Gets the pollutant gas result sets.
	 *
	 * @param vehicleFileId the vehicle file id
	 * @return the pollutant gas result sets
	 */
	List<PollutantGasResultSet> getPollutantGasResultSets(Long vehicleFileId);

	/**
	 * Save pollutant gas result set.
	 *
	 * @param pollutantGasResultSetObj the pollutant gas result set obj
	 * @return the pollutant gas result set
	 */
	PollutantGasResultSet savePollutantGasResultSet(PollutantGasResultSet pollutantGasResultSetObj);

	/**
	 * Gets the max order.
	 *
	 * @param currentSample the current sample
	 * @return the max order
	 */
	Integer getMaxOrder(StatisticalSample currentSample);

	/**
	 * Gets the last i result sets for sample.
	 *
	 * @param currentSample the current sample
	 * @param i the i
	 * @return the last i result sets for sample
	 */
	List<PollutantGasResultSet> getLastIResultSetsForSample(StatisticalSample currentSample, int i);

}
