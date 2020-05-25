package com.inetpsa.poc00.domain.generictestconditionmandatory;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of GenericTestConditionMandatory.
 */

public interface GenericTestCMRepository extends GenericRepository<GenericTestConditionMandatory, Long> {

    /**
     * Saves the GenericTestConditionMandatory.
     *
     * @param object the GenericTestConditionMandatory to save
     * @return the GenericTestConditionMandatory
     */
    GenericTestConditionMandatory saveGenericTestCM(GenericTestConditionMandatory object);

    /**
     * Persists the GenericTestConditionMandatory.
     *
     * @param object the GenericTestConditionMandatory to persist
     */
    void persistGenericTestCDM(GenericTestConditionMandatory object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return GenericTestConditionMandatory count
     */
    long count();
}
