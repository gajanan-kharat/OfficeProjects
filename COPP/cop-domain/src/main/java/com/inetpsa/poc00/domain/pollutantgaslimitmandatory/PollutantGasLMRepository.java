package com.inetpsa.poc00.domain.pollutantgaslimitmandatory;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of PollutantGasLmtMandatory.
 */

public interface PollutantGasLMRepository extends GenericRepository<PollutantGasLmtMandatory, Long> {

    /**
     * Saves the PollutantGasLmtMandatory.
     *
     * @param object the PollutantGasLmtMandatory to save
     * @return the PollutantGasLmtMandatory
     */
    PollutantGasLmtMandatory savePollGasLMandt(PollutantGasLmtMandatory object);

    /**
     * Persists the PollutantGasLmtMandatory.
     *
     * @param object the PollutantGasLmtMandatory to persist
     */
    void persistPollGasLMandt(PollutantGasLmtMandatory object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return PollutantGasLmtMandatory count
     */
    long count();
}
