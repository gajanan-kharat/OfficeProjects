package com.inetpsa.poc00.domain.factorcoeffLabel;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of FactorCoefficentsLabel.
 */

public interface FactorCoeffLabelRepository extends GenericRepository<FactorCoefficentsLabel, Long> {

    /**
     * Saves the FactorCoefficentsLabel.
     *
     * @param object the FactorCoefficentsLabel to save
     * @return the FactorCoefficentsLabel
     */
    FactorCoefficentsLabel saveFactorCoeffLabel(FactorCoefficentsLabel object);

    /**
     * Persists the FactorCoefficentsLabel.
     *
     * @param object the FactorCoefficentsLabel to persist
     */
    void persistFactorCoeffLabel(FactorCoefficentsLabel object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return FactorCoefficentsLabel count
     */
    long count();
}
