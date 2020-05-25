package com.inetpsa.poc00.domain.pollutantgaslabel;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of PollutantGasPollutantGasLmtMandatory.
 */

public interface PollutantGasLabelRepository extends GenericRepository<PollutantGasLabel, Long> {

	/**
	 * Saves the PollutantGasPollutantGasLmtMandatory.
	 * 
	 * @param object the PollutantGasPollutantGasLmtMandatory to save
	 * @return the PollutantGasPollutantGasLmtMandatory
	 */
	PollutantGasLabel savePollutantGasLabel(PollutantGasLabel object);

	/**
	 * Persists the PollutantGasPollutantGasLmtMandatory.
	 *
	 * @param object the PollutantGasPollutantGasLmtMandatory to persist
	 * @return the pollutant gas label
	 */
	PollutantGasLabel persistPollutantGasLabel(PollutantGasLabel object);

	/**
	 * Delete all categories.
	 *
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return PollutantGasPollutantGasLmtMandatory count
	 */
	long count();

	/**
	 * Delete pollutant gas label.
	 *
	 * @param id the id
	 * @return the int
	 */
	int deletePollutantGasLabel(Long id);

	/**
	 * Gets the pollutant by label.
	 *
	 * @param pollutantGasLabel the pollutant gas label
	 * @return the pollutant by label
	 */
	List<PollutantGasLabel> getPollutantByLabel(String pollutantGasLabel);

	/**
	 * Gets the used pollutant labels.
	 *
	 * @param emissionStdId the emission std id
	 * @return the used pollutant labels
	 */
	List<PollutantGasLabel> getAllUsedPgLabels(Long emissionStdId);

	/**
	 * Load poll gas label.
	 *
	 * @param pollGasLabelId the poll gas label id
	 * @return the pollutant gas label
	 */
	PollutantGasLabel loadPollGasLabel(long pollGasLabelId);
}
